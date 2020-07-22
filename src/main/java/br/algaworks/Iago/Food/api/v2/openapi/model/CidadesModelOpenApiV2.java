package br.algaworks.Iago.Food.api.v2.openapi.model;

import br.algaworks.Iago.Food.api.v2.model.dto.CidadeModelV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("CidadesModel")
public class CidadesModelOpenApiV2 {

    private CidadeModelEmbeddedOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("CidadeModelEmbedded")
    private static class CidadeModelEmbeddedOpenApi {
        private List<CidadeModelV2> cidades;

    }
}
