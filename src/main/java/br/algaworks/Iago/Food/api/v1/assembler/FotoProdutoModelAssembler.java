package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.RestauranteProdutoFotoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.FotoProdutoModel;
import br.algaworks.Iago.Food.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public FotoProdutoModelAssembler() {
        super(RestauranteProdutoFotoController.class, FotoProdutoModel.class);
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = modelMapper.map(fotoProduto, FotoProdutoModel.class);

        fotoProdutoModel.add(links.linkToProdutoFoto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
        fotoProdutoModel.add(links.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));

        return fotoProdutoModel;
    }

    @Override
    public CollectionModel<FotoProdutoModel> toCollectionModel(Iterable<? extends FotoProduto> entities) {
        return super.toCollectionModel(entities);
    }
}
