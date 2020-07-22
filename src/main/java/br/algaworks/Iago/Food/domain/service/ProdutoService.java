package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.ProdutoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.Produto;
import br.algaworks.Iago.Food.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteService restauranteService;

    public Produto buscar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId).orElseThrow(() ->
                new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
}
