package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.GrupoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.GrupoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Usuario;
import br.algaworks.Iago.Food.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private ApiLinks links;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        var grupoCollectionModel = grupoModelAssembler
                .toCollectionModel(usuario.getGrupos())
                    .removeLinks()
                    .add(links.linkToGruposUsuarioAssociacao(usuarioId, "associar"));

        grupoCollectionModel.getContent().forEach(grupoModel -> {
            grupoModel.add(links.linkToGruposUsuariodesassociacao(
                    usuarioId, grupoModel.getId(), "desassociar"));
        });

        return grupoCollectionModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
