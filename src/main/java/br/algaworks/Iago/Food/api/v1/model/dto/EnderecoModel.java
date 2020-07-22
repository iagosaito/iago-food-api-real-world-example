package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @ApiModelProperty(example = "12321879")
    private String cep;

    @ApiModelProperty(example = "Rua das Palmeiras")
    private String logradouro;

    @ApiModelProperty(example = "896")
    private String numero;

    @ApiModelProperty(example = "Lado B")
    private String complemento;

    @ApiModelProperty(example = "Jardim das Flores")
    private String bairro;

    private CidadeResumoModel cidade;

}
