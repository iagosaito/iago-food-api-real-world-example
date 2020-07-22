package br.algaworks.Iago.Food.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format("Não existe um cadastro de Produto com o id %d no Restaurante de id %s", produtoId, restauranteId));
    }

}
