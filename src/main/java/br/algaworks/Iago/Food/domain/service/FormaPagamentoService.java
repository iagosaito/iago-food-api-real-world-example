package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.exception.FormaPagamentoNaoEncontradaException;
import br.algaworks.Iago.Food.domain.model.FormaPagamento;
import br.algaworks.Iago.Food.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento de id %d não pode ser excluída, pois está em uso";

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(id);
        }
    }
}
