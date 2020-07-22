package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoInput {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long produtoId;

    @ApiModelProperty(example = "1")
    @PositiveOrZero
    @NotNull
    private Integer quantidade;

    @ApiModelProperty(example = "Sem cebola")
    private String observacao;
}
