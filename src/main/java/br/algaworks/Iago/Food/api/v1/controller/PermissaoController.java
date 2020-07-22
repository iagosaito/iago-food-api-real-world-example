package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.api.v1.assembler.PermissaoModelAssembler;
import br.algaworks.Iago.Food.api.v1.model.dto.PermissaoModel;
import br.algaworks.Iago.Food.core.security.CheckSecurity;
import br.algaworks.Iago.Food.domain.model.Permissao;
import br.algaworks.Iago.Food.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements br.algaworks.Iago.Food.api.v1.openapi.controller.PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> permissoes = permissaoRepository.findAll();
        return permissaoModelAssembler.toCollectionModel(permissoes);
    }

}
