package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {

    @ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Prime Rib ao molho inglês")
    private String descricao;

    @ApiModelProperty(example = "500kb")
    private Long tamanho;

    @ApiModelProperty(example = "image/png")
    private String contentType;
}
