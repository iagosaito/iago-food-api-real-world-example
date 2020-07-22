package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("CidadesEmbeddedModel")
    private static class CidadeEmbeddedModelOpenApi{
        private List<CidadeModel> cidades;
    }
}
