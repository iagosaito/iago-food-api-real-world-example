package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.CidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Estado;
import br.algaworks.Iago.Food.domain.repository.CidadeRepository;
import br.algaworks.Iago.Food.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade buscar(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long idEstado = cidade.getEstado().getId();

        Estado estado = estadoService.buscar(idEstado);

//        Estado estado = estadoRepository.findById(idEstado)
//                .orElseThrow(() ->
//                        new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, idEstado)));

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id){
        try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }
}
