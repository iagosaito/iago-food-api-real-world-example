package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os Grupos associados a um Usuários")
    CollectionModel<GrupoModel> listar(@ApiParam(value = "ID de um Usuário", example = "1", required = true)
                                    Long usuarioId);

    @ApiOperation("Associa um Grupo a um novo Usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário associado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou Grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID de um Usuário", example = "1", required = true)
                               Long usuarioId,
                  @ApiParam(value = "ID de um Grupo", example = "1", required = true)
                               Long grupoId);

    @ApiOperation("Desassocia um Grupo a um novo Usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário desassociado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou Grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um Usuário", example = "1", required = true)
                                  Long usuarioId,
                                     @ApiParam(value = "ID de um Grupo", example = "1", required = true)
                                  Long grupoId);
}
