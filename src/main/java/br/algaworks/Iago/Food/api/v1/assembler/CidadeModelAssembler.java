package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.CidadeController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import br.algaworks.Iago.Food.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public CidadeModelAssembler() {
        super(CidadeController.class, CidadeModel.class);
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {

        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

//        CidadeModel cidadeModel =  modelMapper.map(cidade, CidadeModel.class);
//
//        cidadeModel.add(linkTo(methodOn(CidadeController.class)
//                .buscar(cidadeModel.getId())).withSelfRel());

        cidadeModel.add(
                links.linkToCidades("cidades")
        );

        cidadeModel.getEstado().add(
                links.linkToEstado(cidadeModel.getEstado().getId())
        );

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

    //    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
//        return cidades.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }

}
