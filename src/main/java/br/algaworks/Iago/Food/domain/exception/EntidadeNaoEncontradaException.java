package br.algaworks.Iago.Food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class EntidadeNaoEncontradaException extends NegocioException {
//public class EntidadeNaoEncontradaException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException (String mensagem){
        super(mensagem);
    }

    /*
    public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
        super(status, mensagem);
    }


    public EntidadeNaoEncontradaException (String mensagem){
        this(HttpStatus.NOT_FOUND, mensagem);
    }
    */


}
