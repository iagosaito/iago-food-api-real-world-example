package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.exception.GrupoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Grupo;
import br.algaworks.Iago.Food.domain.model.Permissao;
import br.algaworks.Iago.Food.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "O Grupo de id %d não pode ser excluído, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoService permissaoService;

    public Grupo buscar(Long id) {
        return grupoRepository.findById(id).orElseThrow(() ->
                new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);

        grupo.removerPermissao(permissao);
    }
}
