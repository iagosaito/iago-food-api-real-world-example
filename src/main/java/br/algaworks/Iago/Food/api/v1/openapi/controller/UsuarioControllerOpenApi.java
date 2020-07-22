package br.algaworks.Iago.Food.api.v1.openapi.controller;

import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import br.algaworks.Iago.Food.api.v1.model.input.SenhaInput;
import br.algaworks.Iago.Food.api.v1.model.input.UsuarioComSenhaInput;
import br.algaworks.Iago.Food.api.v1.model.input.UsuarioInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os Usuários")
    CollectionModel<UsuarioModel> listar();

    @ApiOperation("Busca um Usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do Usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel buscar(@ApiParam(value = "ID do Usuário", example = "1", required = true)
                                Long id);

    @ApiOperation("Cadastra um Usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado com sucesso")
    })
    UsuarioModel salvar(@ApiParam(value = "Representação de um novo Usuário")
                                UsuarioComSenhaInput usuarioInput);

    @ApiOperation("Atualiza um Usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel atualizar(@ApiParam(value = "Representação de novos dados de um Usuário (sem senha)")
                                   UsuarioInput usuarioInput,
                           @ApiParam(value = "ID do Usuário", example = "1", required = true)
                                   Long id);

    @ApiOperation("Exclui um Usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário excluído com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void excluir(@ApiParam(value = "ID do Usuário", example = "1", required = true)
                         Long id);

    @ApiOperation("Atualiza a senha de um Usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void atualizarSenha(@ApiParam(value = "ID do Usuário", example = "1", required = true)
                                Long id,
                        @ApiParam(value = "Representação dos dados de uma nova senha para um Usuário", required = true)
                                SenhaInput senhaInput);
}
