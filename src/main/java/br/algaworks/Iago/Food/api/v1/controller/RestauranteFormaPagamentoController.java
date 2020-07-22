package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.FormaPagamentoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private ApiLinks links;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel =
                formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamentos())
                    .removeLinks()
                    .add(links.linkToRestauranteFormasPagamento(restauranteId))
                    .add(links.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));


        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
            formaPagamentoModel.add(links.linkToRestauranteFormaPagamentoDesassociacao(
                    restauranteId, formaPagamentoModel.getId(), "desassociar"));
        });

        return formasPagamentoModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}

