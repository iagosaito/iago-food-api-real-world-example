package br.algaworks.Iago.Food.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PageModel")
public class PagedModelOpenApi {

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private Long size;

    @ApiModelProperty(example = "50", value = "Quantidade total de elementos")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Quantidade total de páginas com base no Size")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Número da página atual (começa em 0)")
    private Long number;

}
