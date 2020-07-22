package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Spaguetti ao Pesto")
    private String nome;

    @ApiModelProperty(example = "Spaguetti ao Pesto com molho pecorino romano")
    private String descricao;

    @ApiModelProperty(example = "25.00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}
