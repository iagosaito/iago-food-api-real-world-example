package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.PermissaoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as Permissões associadas a um Grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID do Grupo", example = "1", required = true)
                                        Long grupoId);

    @ApiOperation("Desassocia uma Permissão a um Grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão desassociada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo ou Permissão não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do Grupo", example = "1", required = true) Long grupoId,
                     @ApiParam(value = "ID da Permissão", example = "1", required = true) Long permissaoId);

    @ApiOperation("Associa uma Permissão a um Grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão associada com sucesso", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo ou Permissão não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do Grupo", example = "1", required = true) Long grupoId,
                  @ApiParam(value = "ID da Permissão", example = "1", required = true) Long permissaoId);
}
