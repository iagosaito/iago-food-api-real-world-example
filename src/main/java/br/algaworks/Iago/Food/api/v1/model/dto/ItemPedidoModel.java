package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Macarrão Miojo")
    private String produtoNome;

    @ApiModelProperty(example = "10")
    private Integer quantidade;

    @ApiModelProperty(example = "10.00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "100.00")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Sem talheres, por favor")
    private String observacao;
}
