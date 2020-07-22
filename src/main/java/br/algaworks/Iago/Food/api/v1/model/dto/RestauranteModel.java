package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Tuk Tuk Comida Indiana")
    private String nome;

    @ApiModelProperty(example = "9.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

    private EnderecoModel endereco;

    @ApiModelProperty(example = "true")
    private Boolean ativo;

    @ApiModelProperty(example = "true")
    private Boolean aberto;

}
