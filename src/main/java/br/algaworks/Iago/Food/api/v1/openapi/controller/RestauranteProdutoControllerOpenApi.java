package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.ProdutoModel;
import br.algaworks.Iago.Food.api.v1.model.input.ProdutoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os Produtos de um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<ProdutoModel> listar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                      Long restauranteId,
                              @ApiParam(value = "Incluir ou não inativos na busca", defaultValue = "false", example = "true") Boolean incluirInativos);

    @ApiOperation("Busca um Produto de um Restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Restaurante ou do Produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou Produto não encontrado", response = Problem.class)
    })
    ProdutoModel buscar(@ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                Long restauranteId,
                        @ApiParam(value = "ID do Produto", example = "1", required = true)
                                Long produtoId);

    @ApiOperation("Cadastra um novo Produto de um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoModel salvar(@ApiParam(value = "Representação de um novo Produto", required = true)
                                ProdutoInput produtoInput,
                        @ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                Long restauranteId);

    @ApiOperation("Atualiza um Produto de um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou Produto não encontrado", response = Problem.class)
    })
    ProdutoModel atualizar(@ApiParam(value = "Representação de novos dados de um Produto", required = true)
                                   ProdutoInput produtoInput,
                           @ApiParam(value = "ID do Restaurante", example = "1", required = true)
                                   Long restauranteId,
                           @ApiParam(value = "ID do Produto", example = "1", required = true)
                                   Long produtoId);
}
