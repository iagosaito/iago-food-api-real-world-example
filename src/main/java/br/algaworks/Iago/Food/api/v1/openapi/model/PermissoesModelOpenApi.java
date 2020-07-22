package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("PermissoesModel")
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("PermissoesEmbedded")
    private static class PermissoesEmbeddedOpenApi {
        private List<PermissaoModel> permissoes;
    }
}
