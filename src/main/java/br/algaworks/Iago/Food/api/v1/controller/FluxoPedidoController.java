package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @CheckSecurity.Pedidos.PodeGerenciarPedido
    @Override
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        fluxoPedidoService.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedido
    @Override
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        fluxoPedidoService.entregar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedido
    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelamento(@PathVariable String codigoPedido) {
        fluxoPedidoService.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

}
