package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.EstadoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.EstadoModel;
import br.algaworks.Iago.Food.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = modelMapper.map(estado, EstadoModel.class);

        estadoModel.add(
                links.linkToEstado(estadoModel.getId(), "estados")
        );

        estadoModel.add(
                links.linkToEstado(estadoModel.getId())
        );

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities).add(linkTo(EstadoController.class).withSelfRel());
    }

    //    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
//        return estados.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }

}
