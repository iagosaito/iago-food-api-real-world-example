package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.PermissaoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Permissao;
import br.algaworks.Iago.Food.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscar(Long id) {
        return permissaoRepository.findById(id).orElseThrow(() ->
                new PermissaoNaoEncontradoException(id));
    }
}
