package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.RestauranteBasicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("RestaurantesBasicoModel")
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesBasicoModelOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("RestauranteBasicoModelEmbedded")
    private static class RestaurantesBasicoModelEmbeddedOpenApi {
        private List<RestauranteBasicoModel> restaurantes;
    }
}
