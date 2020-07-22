package br.algaworks.Iago.Food.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInput {

    @ApiModelProperty(example = "Cartão de Débito", required = true)
    @NotBlank
    private String descricao;

}
