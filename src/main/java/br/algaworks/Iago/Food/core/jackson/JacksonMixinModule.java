package br.algaworks.Iago.Food.core.jackson;

import br.algaworks.Iago.Food.api.v1.model.mixin.CidadeMixin;
import br.algaworks.Iago.Food.api.v1.model.mixin.CozinhaMixin;
import br.algaworks.Iago.Food.api.v1.model.mixin.EstadoMixin;
import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.model.Estado;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
//        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Estado.class, EstadoMixin.class);
    }
}
