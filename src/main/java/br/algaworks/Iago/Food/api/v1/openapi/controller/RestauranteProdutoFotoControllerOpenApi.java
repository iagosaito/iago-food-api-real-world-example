package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.dto.FotoProdutoModel;
import br.algaworks.Iago.Food.api.v1.model.input.FotoProdutoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation("Atualiza uma Foto do Produto de um Restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto atualizada com sucesso"),
            @ApiResponse(code = 400, message = "ID do Produto ou Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto ou Restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoModel atualizarFoto(@ApiParam(value = "ID de um Restaurante", example = "1", required = true)
                                           Long restauranteId,
                                   @ApiParam(value = "ID de um Produto", example = "1", required = true)
                                           Long produtoId,
                                   @ApiParam(value = "Representação de uma foto", required = true)
                                           FotoProdutoInput fotoProdutoInput,
                                   @ApiParam(value = "Arquivo da foto do produto. (Máximo 900kb e em formatos PNG ou JPEG)", required = true) MultipartFile arquivo) throws IOException;

    @ApiOperation(value = "Busca uma Foto do Produto de um Restaurante por ID",
            produces = "application/json, image/png, image/jpeg")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Produto ou Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto ou Restaurante não encontrado", response = Problem.class)
    })
    FotoProdutoModel buscarFoto(@ApiParam(value = "ID de um Restaurante", example = "1", required = true)
                                        Long restauranteId,
                                @ApiParam(value = "ID de um Produto", example = "1", required = true)
                                        Long produtoId);

    @ApiOperation(value = "Busca a representação binária da foto", hidden = true)
    ResponseEntity<?> servirFoto(@ApiParam(value = "ID de um Restaurante", example = "1", required = true)
                                         Long restauranteId,
                                 @ApiParam(value = "ID de um Produto", example = "1", required = true)
                                         Long produtoId,
                                 @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Exclui uma Foto do Produto de um Restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto excluída com sucesso"),
            @ApiResponse(code = 404, message = "ID do Produto do Restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto ou Restaurante não encontrado", response = Problem.class)
    })
    void excluirFoto(@ApiParam(value = "ID de um Restaurante", example = "1", required = true)
                             Long restauranteId,
                     @ApiParam(value = "ID de um Produto", example = "1", required = true)
                             Long produtoId);
}
