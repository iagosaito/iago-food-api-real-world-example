package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.UsuarioModelAssembler;
import br.algaworks.Iago.Food.api.v1.openapi.controller.UsuarioControllerOpenApi;
import br.algaworks.Iago.Food.api.v1.disassembler.UsuarioInputDisassembler;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import br.algaworks.Iago.Food.api.v1.model.input.SenhaInput;
import br.algaworks.Iago.Food.api.v1.model.input.UsuarioInput;
import br.algaworks.Iago.Food.api.v1.model.input.UsuarioComSenhaInput;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Usuario;
import br.algaworks.Iago.Food.domain.repository.UsuarioRepository;
import br.algaworks.Iago.Food.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModel buscar(@PathVariable Long id) {
        return usuarioModelAssembler.toModel(usuarioService.buscar(id));
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        return usuarioModelAssembler.toModel(usuario);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @Override
    @PutMapping("/{id}")
    public UsuarioModel atualizar(@RequestBody @Valid UsuarioInput usuarioInput,
                                                  @PathVariable Long id) {
        Usuario usuario = usuarioService.buscar(id);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);

        return usuarioModelAssembler.toModel(usuarioService.salvar(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @Override
    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        Usuario usuario = usuarioService.buscar(id);

        usuarioService.atualizarSenha(usuario, senhaInput);
    }

}
