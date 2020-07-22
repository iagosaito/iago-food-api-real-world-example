package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.EstadoModel;
import br.algaworks.Iago.Food.api.v1.model.input.EstadoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation(value = "Lista os Estados")
    CollectionModel<EstadoModel> listar();

    @ApiOperation(value = "Busca um Estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    ResponseEntity<EstadoModel> buscar(@ApiParam(value = "ID do Estado", example = "1", required = true)
                                               Long id);

    @ApiOperation(value = "Cadastra um Estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado com sucesso")
    })
    EstadoModel adicionar(@ApiParam(value = "Representação de um novo Estado", required = true)
                                             EstadoInput estadoInput);

    @ApiOperation(value = "Atualiza um Estado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModel atualizar(@ApiParam(value = "Representação de novos dados de um Estado", required = true)
                                             EstadoInput estadoInput,
                                     @ApiParam(value = "ID do Estado", example = "1", required = true)
                                             Long id);

    @ApiOperation(value = "Atualiza um Estado")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído com sucesso"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID do Estado", example = "1", required = true) Long id);
}
