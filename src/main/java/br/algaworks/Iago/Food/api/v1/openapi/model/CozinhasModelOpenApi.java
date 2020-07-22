package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;

    @Getter
    @Setter
    @ApiModel("CidadesEmbeddedModel")
    private static class CozinhasEmbeddedModelOpenApi{
        private List<CozinhaModel> cozinhas;
    }

}
