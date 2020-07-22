package br.algaworks.Iago.Food.core.email;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@ConfigurationProperties("iagofood.email")
@Component
@Getter
@Setter
public class EmailProperties {

    @NotNull
    private String remetente;

    @NonNull
    private Implementacao impl = Implementacao.FAKE;

    @NonNull
    private SandBox sandBox = new SandBox();

    public enum Implementacao {
        FAKE, SMTP, SANDBOX
    }

    @Getter
    @Setter
    public static class SandBox {
        private String destinatario;
    }
}
