package br.algaworks.Iago.Food.api.v1.model.mixin;

import br.algaworks.Iago.Food.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;

}
