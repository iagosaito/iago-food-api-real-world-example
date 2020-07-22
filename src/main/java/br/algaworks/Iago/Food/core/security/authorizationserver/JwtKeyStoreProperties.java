package br.algaworks.Iago.Food.core.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "iagofood.jwt.keystore", ignoreUnknownFields = false)
public class JwtKeyStoreProperties {

    private String path;

    private String password;

    private String keypairAlias;
}
