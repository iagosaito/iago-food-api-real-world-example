package br.algaworks.Iago.Food.core.security.authorizationserver;

import br.algaworks.Iago.Food.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);

        this.fullName = usuario.getNome();
        this.userId = usuario.getId();
    }
}
