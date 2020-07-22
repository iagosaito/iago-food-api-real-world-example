package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.CozinhaController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public CozinhaModelAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);

        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(
                links.linkToCozinhas("cozinhas")
        );

        return cozinhaModel;
    }

//    public List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
//        return cozinhas.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
