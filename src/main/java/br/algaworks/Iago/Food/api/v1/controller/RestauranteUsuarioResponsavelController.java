package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.UsuarioModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.UsuarioInputDisassembler;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
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
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    private ApiLinks links;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        var usuarioCollectionModel = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(links.linkToResponsaveisRestaurante(restauranteId))
                .add(links.linkToRestauranteUsuariosAssociacao(restauranteId, "associar"));

        usuarioCollectionModel.getContent().forEach(usuarioModel -> {
            usuarioModel.add(links.linkToRestauranteUsuariosDesassociacao(restauranteId, usuarioModel.getId(), "desassociar"));
        });

        return usuarioCollectionModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarUsuarioResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @Override
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarUsuarioResponsavel(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}
