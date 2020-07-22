package br.algaworks.Iago.Food.domain.exception;

public class PermissaoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PermissaoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public PermissaoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de Permissão com o id %d", id));
    }

}
