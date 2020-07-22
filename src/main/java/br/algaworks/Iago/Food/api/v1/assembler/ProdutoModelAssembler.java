package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.RestauranteProdutoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.ProdutoModel;
import br.algaworks.Iago.Food.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = modelMapper.map(produto, ProdutoModel.class);

        produtoModel.add(links.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoModel.add(links.linkToProduto(produto.getRestaurante().getId(), produto.getId()));
        produtoModel.add(links.linkToProdutoFoto(produto.getRestaurante().getId(),
                produto.getId(), "foto"));

        return produtoModel;
    }

    @Override
    public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends Produto> entities) {
        return super.toCollectionModel(entities);
    }

    //    public List<ProdutoModel> toCollectionModel(Collection<Produto> produtos) {
//        return produtos.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
