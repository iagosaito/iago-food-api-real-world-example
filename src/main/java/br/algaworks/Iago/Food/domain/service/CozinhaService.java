package br.algaworks.Iago.Food.domain.service;


import br.algaworks.Iago.Food.domain.exception.CozinhaNaoEncontradaException;
import br.algaworks.Iago.Food.domain.exception.EntidadeNaoEncontradaException;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.exception.EntidadeEmUsoException;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }


    // Tratando Exceptions com Catch
    @Transactional
    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    /*
    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format(MSG_COZINHA_NAO_ENCONTRADA, id)
            );
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id)
            );
        }
    }
     */

    // Utilizando ResponseSTATUS

    /*
    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Não existe um cadastro de cozinha com código %d", id)
            );
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de código %d não pode ser removida, " +
                            "pois está em uso", id)
            );
        }
    }

     */
}
