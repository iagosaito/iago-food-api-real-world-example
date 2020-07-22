package br.algaworks.Iago.Food.api.v1.openapi.model;

import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("UsuariosModel")
public class UsuariosModelOpenApi {

    private UsuarioModelEmbeddedOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("UsuarioModelEmbedded")
    private static class UsuarioModelEmbeddedOpenApi {
        private List<UsuarioModel> usuarios;
    }
}
