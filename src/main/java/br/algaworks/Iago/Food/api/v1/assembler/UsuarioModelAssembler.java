package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.CidadeController;
import br.algaworks.Iago.Food.api.v1.controller.UsuarioController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import br.algaworks.Iago.Food.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }

    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = modelMapper.map(usuario, UsuarioModel.class);

        usuarioModel.add(links.linkToUsuarios("usuarios"));

        usuarioModel.add(
                links.linkToUsuario(usuarioModel.getId())
        );

        usuarioModel.add(
                links.linkToGruposUsuario(usuario.getId(), "grupos-usuarios")
        );

        return usuarioModel;
    }

    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities).add(linkTo(CidadeController.class).withSelfRel());
    }

    //    public List<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
//        return usuarios.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
