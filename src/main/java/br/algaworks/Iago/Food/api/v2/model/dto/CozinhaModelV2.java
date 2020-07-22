package br.algaworks.Iago.Food.api.v2.model.dto;

import br.algaworks.Iago.Food.api.v1.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@ApiModel(value = "CozinhaModel")
@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModelV2 extends RepresentationModel<CozinhaModelV2> {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long idCozinha;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestauranteView.Resumo.class)
    private String nomeCozinha;

}
