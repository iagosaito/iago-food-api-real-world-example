package br.algaworks.Iago.Food.core.email;

import br.algaworks.Iago.Food.domain.service.EnvioEmailService;
import br.algaworks.Iago.Food.infrastructure.service.email.FakeEnvioEmailService;
import br.algaworks.Iago.Food.infrastructure.service.email.SandBoxEnvioEmailService;
import br.algaworks.Iago.Food.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService () {

        EmailProperties.Implementacao emailImpl = emailProperties.getImpl();

        if (emailImpl.equals(EmailProperties.Implementacao.FAKE)) {
            return new FakeEnvioEmailService();
        } else if (emailImpl.equals(EmailProperties.Implementacao.SMTP)) {
            return new SmtpEnvioEmailService();
        } else
            return new SandBoxEnvioEmailService();
    }
}
