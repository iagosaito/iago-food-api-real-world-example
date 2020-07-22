package br.algaworks.Iago.Food.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "CozinhaInput")
@Getter
@Setter
public class CozinhaInputV2 {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nomeCozinha; //Tailandesa, Brasileira, Indiana

}
