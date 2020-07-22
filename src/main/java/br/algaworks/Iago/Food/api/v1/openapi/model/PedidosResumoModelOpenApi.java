package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.PedidoResumoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@Getter
@Setter
@Api("PedidosModel")
public class PedidosResumoModelOpenApi {

    private PedidoResumoModelEmbeddedOpenApi _embedded;
    private Link _links;
    private PagedModelOpenApi page;

    @Getter
    @Setter
    @ApiModel("PedidoModelEmbedded")
    private static class PedidoResumoModelEmbeddedOpenApi {
        private List<PedidoResumoModel> pedidos;
    }

}
