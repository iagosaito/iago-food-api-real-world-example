package br.algaworks.Iago.Food.api.v2.openapi.controller;

import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import br.algaworks.Iago.Food.api.v1.model.input.CozinhaInput;
import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import br.algaworks.Iago.Food.api.v2.model.input.CozinhaInputV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

    @ApiOperation(value = "Listar as Cozinhas por paginação")
    PagedModel<CozinhaModelV2> listar(@ApiParam(value = "Parâmetros de paginação")
                              @PageableDefault(size = 10) Pageable pageable);

    @ApiOperation(value = "Busca uma Cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da Cozinha inválido"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada")
    })
    CozinhaModelV2 buscar(@PathVariable Long id);

    @ApiOperation(value = "Cadastra uma Cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada")
    })
    CozinhaModelV2 adicionar(@ApiParam(value = "Representação de uma nova Cozinha")
                      @RequestBody @Valid CozinhaInputV2 cozinhaInput);

    @ApiOperation(value = "Atualiza uma Cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada")
    })
    CozinhaModelV2 atualizar(@ApiParam(value = "ID da Cozinha", example = "1")
                        @PathVariable Long id,
                      @ApiParam(value = "Representação de novos dados de uma Cozinha")
                        @RequestBody @Valid CozinhaInputV2 cozinhaInput);

    @ApiOperation(value = "Exclui uma Cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada")
    })
    void remover(@ApiParam(value = "ID da Cozinha", example = "1")
                 @PathVariable Long id);
}
