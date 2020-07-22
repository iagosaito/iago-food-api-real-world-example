package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.GrupoModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.GrupoInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import br.algaworks.Iago.Food.api.v1.model.input.GrupoInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Grupo;
import br.algaworks.Iago.Food.domain.repository.GrupoRepository;
import br.algaworks.Iago.Food.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<GrupoModel> listar() {
        return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GrupoModel buscar(@PathVariable Long id) {
        return grupoModelAssembler.toModel(grupoService.buscar(id));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        grupo = grupoService.salvar(grupo);

        return grupoModelAssembler.toModel(grupo);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GrupoModel> atualizar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id) {
        Grupo grupo = grupoService.buscar(id);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupo);

        return ResponseEntity.status(HttpStatus.OK).body(grupoModelAssembler.toModel(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        grupoService.excluir(id);
    }
}
