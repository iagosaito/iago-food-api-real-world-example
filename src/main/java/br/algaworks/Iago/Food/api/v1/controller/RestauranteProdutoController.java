package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.ProdutoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.ProdutoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.ProdutoModel;
import br.algaworks.Iago.Food.api.v1.model.input.ProdutoInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Produto;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.repository.ProdutoRepository;
import br.algaworks.Iago.Food.domain.service.ProdutoService;
import br.algaworks.Iago.Food.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ApiLinks links;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId,
                                                @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {

        Restaurante restaurante = restauranteService.buscar(restauranteId);
        List<Produto> produtos;

        if (incluirInativos) {
            produtos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoModelAssembler.toCollectionModel(produtos)
                .add(links.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoModel buscar(@PathVariable Long restauranteId,
                               @PathVariable Long produtoId) {
        Produto produto = produtoService.buscar(restauranteId, produtoId);
        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProdutoModel salvar(@RequestBody @Valid ProdutoInput produtoInput, @PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return produtoModelAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@RequestBody @Valid ProdutoInput produtoInput,
                                  @PathVariable Long restauranteId,
                                  @PathVariable Long produtoId) {
        Produto produto = produtoService.buscar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produto);
        produto = produtoService.salvar(produto);

        return produtoModelAssembler.toModel(produto);
    }

}
