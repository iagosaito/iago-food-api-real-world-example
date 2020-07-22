package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.ProdutoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("ProdutosModel")
public class ProdutosModelOpenApi {

    private ProdutoModelEmbeddedOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("ProdutoModelEmbedded")
    private static class ProdutoModelEmbeddedOpenApi {
        private List<ProdutoModel> produtos;
    }

}
