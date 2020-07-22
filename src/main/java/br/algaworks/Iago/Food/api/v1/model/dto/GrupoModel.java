package br.algaworks.Iago.Food.api.v1.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoModel extends RepresentationModel<GrupoModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerentes")
    private String nome;
}
