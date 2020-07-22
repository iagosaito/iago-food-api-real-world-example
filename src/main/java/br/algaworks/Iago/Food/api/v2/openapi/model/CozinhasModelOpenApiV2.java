package br.algaworks.Iago.Food.api.v2.openapi.model;

import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApiV2 {

    private CozinhaModelEmbeddedOpenApi _embedded;
    private Links _links;
    private Pageable page;

    @Getter
    @Setter
    @ApiModel("CozinhaModelEmbedded")
    private static class CozinhaModelEmbeddedOpenApi {
        private List<CozinhaModelV2> cozinhas;

    }
}
