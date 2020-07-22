package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @ApiModelProperty(example = "1234", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "1234", required = true)
    @NotBlank
    private String novaSenha;
}
