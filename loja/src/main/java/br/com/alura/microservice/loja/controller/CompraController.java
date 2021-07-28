package br.com.alura.microservice.loja.controller;

import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.model.Compra;
import br.com.alura.microservice.loja.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @RequestMapping("/{id}")
    public Compra getById(@PathVariable("id") Long id) {
        return compraService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Compra realizaCompra(@RequestBody CompraDTO compra) {
        return compraService.realizaCompra(compra);
    }
}
