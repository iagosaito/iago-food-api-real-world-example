package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @ApiModelProperty(example = "Macarrão ao Pesto", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Macarrão ao Pesto com molho perocino romano",
            required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "25.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private boolean ativo;
}
