package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoModel;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoResumoModel;
import br.algaworks.Iago.Food.api.v1.model.input.PedidoInput;
import br.algaworks.Iago.Food.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar nas respostas, separados por vírgula",
            name = "campos", type = "string", paramType = "query")
    })
    @ApiOperation("Pesquisa de Pedidos")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Erro nas propriedades do filtro", response = Problem.class)
    })
    PagedModel<PedidoResumoModel> pesquisar(@ApiParam(value = "Representação do Filtro de Pedido") PedidoFilter filter,
                                      @ApiParam(value = "Parâmetros de Paginação") Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar nas respostas, separados por vírgula",
                    name = "campos", type = "string", paramType = "query")
    })
    @ApiOperation("Busca um Pedido por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Pedido inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class),
    })
    PedidoModel buscar(@ApiParam(value = "Código do Pedido (UUID)", example = "9df959ef-caf2-4195-a948-01f4b0123fda")
                               String codigoPedido);

    @ApiOperation("Cadastra um novo Pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido cadastrada")
    })
    ResponseEntity<PedidoModel> salvar(@ApiParam(value = "Representação de um novo Pedido")
                                               PedidoInput pedidoInput);
}
