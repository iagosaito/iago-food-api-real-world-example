package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto foto);
    void delete(FotoProduto foto);
}
