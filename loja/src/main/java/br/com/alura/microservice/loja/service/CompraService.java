package br.com.alura.microservice.loja.service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.model.Compra;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraService {

    private static final Logger log = LoggerFactory.getLogger(CompraService.class);

    @Autowired
    private FornecedorClient fornecedorClient;

    @HystrixCommand(fallbackMethod = "realizaCompraFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")})
    public Compra realizaCompra(CompraDTO compra) {

        final String estado = compra.getEndereco().getEstado();

        log.info("buscando informações do fornecedor de {} ...", estado);
        InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(estado);

        log.info("realizando um pedido");
        InfoPedidoDTO infoPedido = fornecedorClient.realizaPedido(compra.getItens());

        Compra compraSalva = new Compra();
        compraSalva.setPedidoId(infoPedido.getId());
        compraSalva.setTempoDePreparo(infoPedido.getTempoDePreparo());
        compraSalva.setEnderecoDestino(info.getEndereco());

        try {
            log.info("***** Inicializando Thread *****");
            Thread.sleep(2000);
            log.info("***** Finalizando Thread *****");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return compraSalva;
    }

    public Compra realizaCompraFallback(CompraDTO compra) {
        log.info("Fallback invocado ...");
        Compra compraFallback = new Compra();
        compraFallback.setEnderecoDestino(compra.getEndereco().toString());
        return compraFallback;
    }
}
