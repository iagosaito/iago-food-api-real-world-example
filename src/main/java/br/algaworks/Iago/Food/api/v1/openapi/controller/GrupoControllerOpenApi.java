package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import br.algaworks.Iago.Food.api.v1.model.input.GrupoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os Grupos")
    CollectionModel<GrupoModel> listar();

    @ApiOperation("Busca um Grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoModel buscar(@PathVariable Long id);

    @ApiOperation("Cadastra um novo grupo")
    @ApiResponses(@ApiResponse(code = 201, message = "Grupo cadastrado"))
    GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput);

    @ApiOperation("Atualiza um Grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade Atualizada"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<GrupoModel> atualizar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id);

    @ApiOperation("Exclui um Grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void excluir(@PathVariable Long id);
}
