package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.dto.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("FormasPagamentoModel")
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("FormasPagamentoEmbedded")
    private static class FormasPagamentoEmbeddedOpenApi {
        private List<FormaPagamentoModel> formasPagamento;
    }
}
