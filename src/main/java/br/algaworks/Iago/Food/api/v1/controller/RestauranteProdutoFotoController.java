package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.FotoProdutoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.model.dto.FotoProdutoModel;
import br.algaworks.Iago.Food.api.v1.model.input.FotoProdutoInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.model.FotoProduto;
import br.algaworks.Iago.Food.domain.model.Produto;
import br.algaworks.Iago.Food.domain.service.CatalogoFotoProdutoService;
import br.algaworks.Iago.Food.domain.service.FotoStorageService;
import br.algaworks.Iago.Food.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private CatalogoFotoProdutoService fotoProdutoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FotoStorageService fotoStorageService;

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput,
                                          @RequestPart(required = true) MultipartFile arquivo) throws IOException {

        Produto produto = produtoService.buscar(restauranteId, produtoId);

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());
        fotoProduto.setTamanho(arquivo.getSize());

        FotoProduto fotoProdutoSalva = fotoProdutoService.salvar(fotoProduto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FotoProdutoModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = fotoProdutoService.buscar(restauranteId, produtoId);
        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }


    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId,
                                        @PathVariable Long produtoId,
                                        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = fotoProdutoService.buscar(restauranteId, produtoId);


            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl()).build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.excluir(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
                                                   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {

        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

}
