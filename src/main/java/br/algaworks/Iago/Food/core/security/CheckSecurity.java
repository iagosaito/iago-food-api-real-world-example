package br.algaworks.Iago.Food.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    @interface Cozinhas {
        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND hasAuthority('EDITAR_COZINHAS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditar {}
    }

    @interface Restaurantes {
        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND hasAuthority('EDITAR_RESTAURANTES')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeGerenciarCadastro {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND " +
                "( hasAuthority('EDITAR_RESTAURANTES') OR " +
                "@algaSecurity.gerenciaRestaurantes(#id) )")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeGerenciarFuncionamento {}
    }

    /**
     * returnObject é um alias que funciona apenas em @PostAuthorize.
     * Este alias acessa o retorno do endpoint anotado na requisição.
     */
    @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') OR " +
                "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) OR " +
                "@algaSecurity.gerenciaRestaurantes(returnObject.restaurante.id)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeBuscar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') AND " +
                "( hasAuthority('CONSULTAR_PEDIDOS') OR " +
                "@algaSecurity.gerenciaRestaurantes(#filter.restauranteId) OR " +
                "@algaSecurity.usuarioAutenticadoIgual(#filter.clienteId) )")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodePesquisar {}

        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeGerenciarPedido{}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeCriarPedido{}
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditar {}
    }

    @interface Estado {
        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND hasAuthority('EDITAR_ESTADOS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditar {}
    }

    @interface Cidade {
        @PreAuthorize("hasAuthority('SCOPE_READ') AND isAuthenticated()")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND hasAuthority('EDITAR_CIDADES')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditar {}
    }

    @interface UsuariosGruposPermissoes {

        /**
         * Scopo Write
         * Somente o usuário autenticado pode alterar a próprio senha
         */
        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND " +
                "@algaSecurity.usuarioAutenticadoIgual(#id)")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeAlterarPropriaSenha{}

        /**
         * Scopo Write
         * Usuário autenticado tem a permissão editar_usuário_grupo_permissao
         * ou ser o próprio usuário autenticado
         */
        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND " +
                "( hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') OR " +
                " @algaSecurity.usuarioAutenticadoIgual(#id) )")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeAlterarUsuario{}

        /**
         * Scopo Write
         * Permissão editar_usuario_grupo_permissao
         */
        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND " +
                "hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeEditar{}

        /**
         * Scopo Read
         * Permissão consultar_usuario_grupo_permissao
         */
        @PreAuthorize("hasAuthority('SCOPE_READ') AND " +
                "hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar{}
    }

    @interface Estatisticas {

        @PreAuthorize("hasAuthority('SCOPE_READ') AND " +
                "hasAuthority('GERAR_RELATORIOS')")
        @Target(ElementType.METHOD)
        @Retention(RetentionPolicy.RUNTIME)
        @interface PodeConsultar{}

    }
}


