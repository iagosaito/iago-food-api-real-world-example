package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.v1.model.dto.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as Permissões")
    CollectionModel<PermissaoModel> listar();
}
