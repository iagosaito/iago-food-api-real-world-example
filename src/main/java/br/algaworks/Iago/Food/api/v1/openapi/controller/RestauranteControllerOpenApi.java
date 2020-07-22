package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.v1.openapi.model.RestauranteBasicoModelOpenApi;
import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteApenasNomeModel;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteBasicoModel;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteModel;
import br.algaworks.Iago.Food.api.v1.model.input.RestauranteInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista Restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da projeção de pedidos", name = "projecao",
                paramType = "query", type = "string", allowableValues = "apenas-nome")
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @ApiIgnore
    CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

    @ApiOperation(value = "Busca um Restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<RestauranteModel> buscar(@ApiParam(value = "ID de um Restaurante", example = "1", required = true)
                                                    Long id);

    @ApiOperation(value = "Cadastra um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cadastra um novo Restaurante")
    })
    RestauranteModel adicionar(@ApiParam(value = "Representação de um novo Restaurante", required = true)
                                        RestauranteInput restauranteInput);

    @ApiOperation(value = "Cadastra um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    RestauranteModel atualizar(@ApiParam(value = "Representação de novos dados de um Restaurante") RestauranteInput restauranteInput,
                                @ApiParam(value = "ID de um Restaurante", example = "1", required = true) Long id);

    @ApiOperation(value = "Ativa um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado"),
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> ativar(@ApiParam(value = "ID de um Restaurante", example = "1", required = true) Long id);

    @ApiOperation(value = "Ativa múltiplos Restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados"),
    })
    void ativarMultiplos(@ApiParam(value = "Lista de ID's de Restaurantes", required = true)
                                List<Long> restauranteIds);

    @ApiOperation(value = "Inativa múltiplos Restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes inativados"),
    })
    void desativarMultiplos(@ApiParam(value = "Lista de ID's de Restaurantes", required = true)
                                List<Long> restauranteIds);

    @ApiOperation(value = "Inativa um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado"),
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> inativar(@ApiParam(value = "ID de um Restaurante", example = "1", required = true) Long id);

    @ApiOperation(value = "Fecha um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado"),
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechar(@ApiParam(value = "ID de um Restaurante", example = "1", required = true) Long id);

    @ApiOperation(value = "Abre um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante abertos"),
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> abrir(@ApiParam(value = "ID de um Restaurante", example = "1", required = true) Long id);
}
