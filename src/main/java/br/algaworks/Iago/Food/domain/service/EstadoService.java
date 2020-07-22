package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.EstadoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Estado;
import br.algaworks.Iago.Food.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado buscar(Long id){
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long id){
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }
}
