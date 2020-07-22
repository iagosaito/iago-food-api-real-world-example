package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.PermissaoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.PermissaoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.PermissaoModel;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Grupo;
import br.algaworks.Iago.Food.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private PermissaoInputDisassembler permissaoInputDisassembler;

    @Autowired
    private ApiLinks links;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscar(grupoId);

        var permissaoCollectionModel = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(links.linkToGrupoPermissoes(grupoId))
                .add(links.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissaoCollectionModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(links.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(),
                    "dessasociar"));
        });

        return permissaoCollectionModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
