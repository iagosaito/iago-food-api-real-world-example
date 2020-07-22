package br.algaworks.Iago.Food.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FotoProdutoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de Foto Produto com o id %d no Restaurante de id %s", produtoId, restauranteId));
    }

}
