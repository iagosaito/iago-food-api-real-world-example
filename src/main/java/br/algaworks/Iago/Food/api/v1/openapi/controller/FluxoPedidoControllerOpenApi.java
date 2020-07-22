package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

    @ApiOperation("Confirma um Pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> confirmar(@ApiParam(value = "Código de um Pedido (UUID)", example = "930fb7ac-379b-4858-9306-1f8c3c3ffe0a",
            required = true) String codigoPedido);

    @ApiOperation("Entrega um Pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido entregue com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> entregar(@ApiParam(value = "Código de um Pedido (UUID)", example = "930fb7ac-379b-4858-9306-1f8c3c3ffe0a",
            required = true) String codigoPedido);

    @ApiOperation("Cancela um Pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelamento(@ApiParam(value = "Código de um Pedido (UUID)", example = "930fb7ac-379b-4858-9306-1f8c3c3ffe0a",
            required = true) String codigoPedido);
}
