package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.RestauranteController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteBasicoModel;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public RestauranteBasicoModelAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }


    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteModel = modelMapper.map(restaurante, RestauranteBasicoModel.class);

        restauranteModel.add(links.linkToRestaurantes("restaurantes"));
        restauranteModel.add(links.linkToRestaurante(restauranteModel.getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities).add(links.linkToRestaurantes("restaurantes"));
    }
}
