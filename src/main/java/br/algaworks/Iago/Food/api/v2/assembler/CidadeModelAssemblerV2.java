package br.algaworks.Iago.Food.api.v2.assembler;

import br.algaworks.Iago.Food.api.v1.controller.CidadeController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import br.algaworks.Iago.Food.api.v2.controller.CidadeControllerV2;
import br.algaworks.Iago.Food.api.v2.links.ApiLinksV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CidadeModelV2;
import br.algaworks.Iago.Food.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinksV2 links;

    public CidadeModelAssemblerV2() {
        super(CidadeControllerV2.class, CidadeModelV2.class);
    }

    @Override
    public CidadeModelV2 toModel(Cidade cidade) {

        CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        cidadeModel.add(
                links.linkToCidades("cidades")
        );

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeControllerV2.class).withSelfRel());
    }

}
