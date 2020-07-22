package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("EstadosModel")
public class EstadosModelOpenApi {

    private EstadosEmbeddedOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("EstadosEmbeddedModel")
    private static class EstadosEmbeddedOpenApi {
        private List<EstadoModel> estados;
    }

}
