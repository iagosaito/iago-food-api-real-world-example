package br.algaworks.Iago.Food.core.security;

import br.algaworks.Iago.Food.domain.repository.PedidoRepository;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AlgaSecurity {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Obtendo dados de autenticação da requisição atual
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("usuario_id");
    }

    public boolean gerenciaRestaurantes(Long id) {
        if (id == null) {
            return false;
        }

        return restauranteRepository.existsResponsavel(id, getUsuarioId());
    }

    public boolean isPedidoGerenciadoPor(String codigoPedido) {
        if (!StringUtils.hasText(codigoPedido)) {
            return false;
        }

        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return usuarioId != null && getUsuarioId() != null
                && getUsuarioId().equals(usuarioId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority
                        -> grantedAuthority.getAuthority().equalsIgnoreCase(authorityName));
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
//        @PreAuthorize("hasAuthority('SCOPE_WRITE') AND " +
//                "( hasAuthority('GERENCIAR_PEDIDOS') OR " +
//                " @algaSecurity.isPedidoGerenciadoPor(#codigoPedido) )")
        return hasAuthority("SCOPE_WRITE")
                && hasAuthority("GERENCIAR_PEDIDOS")
                || isPedidoGerenciadoPor(codigoPedido);
    }
}
