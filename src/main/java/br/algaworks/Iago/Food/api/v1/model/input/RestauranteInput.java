package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @ApiModelProperty(example = "McDonalds", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "4.00", required = true)
    @PositiveOrZero
    @NotNull
    private BigDecimal taxaFrete;

    @NotNull
    @Valid
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;

}


