package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as Formas de Pagamento associadas a um Restaurante")
    CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                             Long restauranteId);

    @ApiOperation("Desassocia uma Forma de Pagamento a um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de Pagamento desassociada com sucesso"),
            @ApiResponse(code = 404, message = "Forma de Pagamento ou Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do Restaurante", example = "1", required = true) Long restauranteId,
                     @ApiParam(value = "ID da Forma de Pagamento", example = "1", required = true) Long formaPagamentoId);

    @ApiOperation("Associa uma Forma de Pagamento a um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de Pagamento associada com sucesso"),
            @ApiResponse(code = 404, message = "Forma de Pagamento ou Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do Restaurante", example = "1", required = true) Long restauranteId,
                  @ApiParam(value = "ID da Forma de Pagamento", example = "1", required = true) Long formaPagamentoId);
}
