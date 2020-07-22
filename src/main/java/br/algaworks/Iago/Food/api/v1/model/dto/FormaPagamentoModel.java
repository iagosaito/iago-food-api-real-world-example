package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formas-pagamento")
@Getter
@Setter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

    @ApiModelProperty(example = "1", position = 1)
    private Long id;

    @ApiModelProperty(example = "Cartão de Débito", position = 10)
    private String descricao;

}
