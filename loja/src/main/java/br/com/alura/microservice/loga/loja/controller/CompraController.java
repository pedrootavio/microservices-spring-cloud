package br.com.alura.microservice.loga.loja.controller;

import br.com.alura.microservice.loga.loja.controller.dto.CompraDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @RequestMapping(method = RequestMethod.POST)
    public void realizaCompra(@RequestBody CompraDTO compra) {
        System.out.println("Compra realizada -> " + compra);
    }
}
