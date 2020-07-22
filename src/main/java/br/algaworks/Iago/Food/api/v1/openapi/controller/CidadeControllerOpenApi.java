package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import br.algaworks.Iago.Food.api.v1.model.input.CidadeInput;
import br.algaworks.Iago.Food.domain.model.Cidade;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Listar as Cidades")
    CollectionModel<CidadeModel> listar();

    @ApiOperation("Busca uma Cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da Cidade Inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscar(@ApiParam(value = "ID de uma Cidade", example = "1") Long id);

    @ApiOperation("Cadastra uma nova Cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    ResponseEntity<CidadeModel> adicionar(@ApiParam(name = "CidadeInput", value = "Representação de uma nova cidade")
                                                  CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma Cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade Atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    ResponseEntity<CidadeModel> atualizar(
            @ApiParam(name = "CidadeInput", value = "Representação de uma nova cidade com novos dados")
                CidadeInput cidadeInput,
            @ApiParam(value = "ID de uma Cidade", example = "1")
                Long id);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    ResponseEntity<Cidade> excluir(@ApiParam(value = "Exclui uma cidade", example = "1") Long id);
}
