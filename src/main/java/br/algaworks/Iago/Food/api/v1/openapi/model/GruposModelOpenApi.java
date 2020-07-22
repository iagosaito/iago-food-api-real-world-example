package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.GrupoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("GruposModel")
public class GruposModelOpenApi {

    private GruposEmbeddedOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("GruposEmbedded")
    private static class GruposEmbeddedOpenApi {
        private List<GrupoModel> grupos;
    }
}
