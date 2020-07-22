package br.algaworks.Iago.Food.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de Restaurante com o id %d", id));
    }

}
