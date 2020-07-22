package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.GrupoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import br.algaworks.Iago.Food.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = modelMapper.map(grupo, GrupoModel.class);

        grupoModel.add(links.linkToGrupos("grupos"));
        grupoModel.add(links.linkToGrupo(grupo.getId()));
        grupoModel.add(links.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities).add(links.linkToGrupos());
    }

    //    public List<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
//        return grupos.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
