package br.algaworks.Iago.Food.api.v2.model.input;

import br.algaworks.Iago.Food.api.v1.model.input.EstadoIdInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(value = "CidadeInput")
@Getter
@Setter
public class CidadeInputV2 {

    @ApiModelProperty(example = "São José dos Campos", required = true)
    @NotBlank
    private String nomeCidade;

    @ApiModelProperty(example = "São Paulo", required = true)
    @NotNull
    private Long idEstado;

//    @NotNull
//    @Valid
//    private EstadoIdInput estado;

}
