package br.algaworks.Iago.Food.api.v2.links;

import br.algaworks.Iago.Food.api.v1.controller.*;
import br.algaworks.Iago.Food.api.v2.controller.CidadeControllerV2;
import br.algaworks.Iago.Food.api.v2.controller.CozinhaControllerV2;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public final class ApiLinksV2 {

    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerV2.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaControllerV2.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }
}
