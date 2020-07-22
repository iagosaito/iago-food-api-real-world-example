package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {

    @ApiModelProperty(example = "Bonoro", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "bonoro@iagofood.com", required = true)
    @NotBlank
    @Email
    private String email;
}
