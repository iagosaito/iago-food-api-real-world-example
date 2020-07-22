package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.RestauranteController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteModel;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = modelMapper.map(restaurante, RestauranteModel.class);

        restauranteModel.add(links.linkToRestaurantes("restaurantes"));
        restauranteModel.add(links.linkToRestaurante(restauranteModel.getId()));

        restauranteModel.add(links.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));
        restauranteModel.add(links.linkToRestauranteUsuariosResponsaveis(restauranteModel.getId(), "usuarios-responsaveis"));

        restauranteModel.getCozinha().add(links.linkToCozinha(restauranteModel.getCozinha().getId()));

        if (restauranteModel.getEndereco() != null) {
            restauranteModel.getEndereco().getCidade()
                    .add(links.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(links.linkToRestauranteFechamento(restauranteModel.getId(), "fechar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(links.linkToRestauranteAbertura(restauranteModel.getId(), "abrir"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(links.linkToRestauranteInativacao(restauranteModel.getId(), "inativar"));
        }

        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(links.linkToRestauranteAtivacao(restauranteModel.getId(), "ativar"));
        }

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities);
    }

}
