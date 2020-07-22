package br.algaworks.Iago.Food.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public EstadoNaoEncontradoException (Long id) {
        this(String.format("Não existe um cadastro de Estado com o id %d", id));
    }

}
