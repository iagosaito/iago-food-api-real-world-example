package br.algaworks.Iago.Food.api.v2.assembler;

import br.algaworks.Iago.Food.api.v1.controller.CozinhaController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import br.algaworks.Iago.Food.api.v2.controller.CozinhaControllerV2;
import br.algaworks.Iago.Food.api.v2.links.ApiLinksV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinksV2 links;

    public CozinhaModelAssemblerV2() {
        super(CozinhaControllerV2.class, CozinhaModelV2.class);
    }

    @Override
    public CozinhaModelV2 toModel(Cozinha cozinha) {
        CozinhaModelV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(
                links.linkToCozinhas("cozinhas")
        );

        return cozinhaModel;
    }

    @Override
    public CollectionModel<CozinhaModelV2> toCollectionModel(Iterable<? extends Cozinha> entities) {
        return super.toCollectionModel(entities);
    }

    //    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
//        return cozinhas.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
