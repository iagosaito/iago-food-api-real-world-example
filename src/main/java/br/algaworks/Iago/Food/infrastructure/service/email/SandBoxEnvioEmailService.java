package br.algaworks.Iago.Food.infrastructure.service.email;

import br.algaworks.Iago.Food.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SandBoxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(emailProperties.getSandBox().getDestinatario());

        return mimeMessage;
    }
}
