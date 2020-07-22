package br.algaworks.Iago.Food.api.v1.model.mixin;

import br.algaworks.Iago.Food.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes;

}
