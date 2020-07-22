package br.algaworks.Iago.Food.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public CozinhaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de Cozinha com o id %d", id));
    }

}
