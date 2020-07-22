package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Tuk Tuk Comida Indiana")
    private String nome;

    @ApiModelProperty(example = "9.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;
}
