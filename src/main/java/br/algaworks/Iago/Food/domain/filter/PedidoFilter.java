package br.algaworks.Iago.Food.domain.filter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@ApiModel(value = "Pedido Filter")
@Setter
@Getter
public class PedidoFilter {

    @ApiModelProperty(example = "1")
    private Long clienteId;

    @ApiModelProperty(example = "1")
    private Long restauranteId;

    @ApiModelProperty(example = "2020-02-02T05:30:23Z")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @ApiModelProperty(example = "2020-02-02T07:30:23Z")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

}
