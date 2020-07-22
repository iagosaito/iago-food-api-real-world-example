package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @ApiModelProperty(example = "12321720", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Marechal Deodoro da Fonseca", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "459", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Lado B")
    private String complemento;

    @ApiModelProperty(example = "Consolação")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdInput cidade;

}
