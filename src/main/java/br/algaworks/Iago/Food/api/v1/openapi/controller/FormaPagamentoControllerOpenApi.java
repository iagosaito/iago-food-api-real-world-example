package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import br.algaworks.Iago.Food.api.v1.model.input.FormaPagamentoInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation(value = "Lista as Formas de Pagamento", response = FormasPagamentoModelOpenApi.class)
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation(value = "Busca uma Forma de Pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da Forma de Pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID da Forma de Pagamento", example = "1") Long id, ServletWebRequest request);

    @ApiOperation(value = "Cadastra uma Forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de Pagamento cadastrada")
    })
    ResponseEntity<FormaPagamentoModel> salvar(@ApiParam(value = "Representação de uma Forma de Pagamento")
                                                       FormaPagamentoInput formaPagamentoInput);

    @ApiOperation(value = "Atualiza uma Forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de Pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    ResponseEntity<FormaPagamentoModel> atualizar(@ApiParam(value = "Representação de novos dados de uma Forma de Pagamento")
                                                          FormaPagamentoInput formaPagamentoInput,
                                                  @ApiParam(value = "ID da Forma de Pagamento", example = "1")
                                                          Long id);

    @ApiOperation(value = "Exclui uma Forma de Pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de Pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de Pagamento não encontrada", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID da Forma de Pagamento", example = "1") Long id);
}
