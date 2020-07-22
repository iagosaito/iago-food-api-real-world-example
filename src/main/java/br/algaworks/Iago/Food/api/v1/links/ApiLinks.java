package br.algaworks.Iago.Food.api.v1.links;

import br.algaworks.Iago.Food.api.v1.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public final class ApiLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return new Link(UriTemplate.of(
                pedidosUrl, PAGINACAO_VARIABLES.concat(filterVariables)), rel) {
        };
    }

    public Link linkToEstatisticasVendaDiarias(String rel) {
        TemplateVariables vendaFilterVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String vendaDiariasUrl = linkTo(methodOn(EstatisticasController.class).consultarVendasDiarias(null, null)).toString();

        return new Link(UriTemplate.of(
                vendaDiariasUrl, vendaFilterVariables), rel);

//        return linkTo(methodOn(EstatisticasController.class).consultarVendasDiarias(null, null))
//                .withRel(rel);
    }

    public Link linkToPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(PedidoController.class)
                .buscar(codigoPedido)).withRel(rel);
    }

    public Link linkToPedido(String codigoPedido) {
        return linkToPedido(codigoPedido, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioId)).withRel(rel);
    }

    public Link linkToUsuario(Long usuarioId) {
        return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuario(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGruposUsuarioAssociacao(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId, null))
                .withRel(rel);
    }

    public Link linkToGruposUsuariodesassociacao(Long usuarioId, Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).desassociar(usuarioId, grupoId))
                .withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoId, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(methodOn(FormaPagamentoController.class).listar(null)).withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToCidade(Long cidadeId, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeId)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstado(Long estadoId, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoId)).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).listar(restauranteId, null)).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutoFoto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).buscarFoto(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToProdutoFoto(Long restauranteId, Long produtoId) {
        return linkToProdutoFoto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public Link linkToCozinha(Long cozinhaId, String rel) {
        return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId)).withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelamento(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurantes(String rel) {
        TemplateVariables templateVariables = new TemplateVariables(
                new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String url = linkTo(RestauranteController.class).toUri().toString();

        return new Link(UriTemplate.of(url, templateVariables), rel);
    }

    public Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId,
                                                             String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToRestauranteUsuariosResponsaveis(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteUsuariosResponsaveis(Long restauranteId) {
        return linkToRestauranteUsuariosResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteUsuariosDesassociacao(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).associar(restauranteId, usuarioId))
                .withRel(rel);
    }

    public Link linkToRestauranteUsuariosAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).associar(restauranteId, null))
                .withRel(rel);
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
    }

    public Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }

    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupo(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoController.class).buscar(grupoId)).withRel(rel);
    }

    public Link linkToGrupo(Long grupoId) {
        return linkToGrupo(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(rel);
    }

    public Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).desassociar(grupoId, permissaoId))
                .withRel(rel);
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).associar(grupoId, null))
                .withRel(rel);
    }

    public Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    public Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    public Link linkToEstatisticas(String rel) {
        return linkTo(EstatisticasController.class).withRel(rel);
    }

}
