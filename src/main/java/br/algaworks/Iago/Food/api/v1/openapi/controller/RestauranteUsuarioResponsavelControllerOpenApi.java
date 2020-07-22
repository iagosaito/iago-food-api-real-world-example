package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os Usuários associdados ao Restaurante")
    CollectionModel<UsuarioModel> listar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                      Long restauranteId);

    @ApiOperation("Associa um Usuário a um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário associado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou Usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                          Long restauranteId,
                  @ApiParam(value = "ID do Usuário", example = "1", required = true)
                          Long usuarioId);

    @ApiOperation("Desassocia um Usuário a um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário associado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou Usuário não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                             Long restauranteId,
                     @ApiParam(value = "ID do Usuário", example = "1", required = true)
                             Long usuarioId);
}
