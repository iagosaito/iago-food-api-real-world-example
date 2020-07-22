package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PermissaoInput {

    @ApiModelProperty(example = "CONSULTAR COZINHAS", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Permite consultar cozinhas", required = true)
    @NotBlank
    private String descricao;

}
