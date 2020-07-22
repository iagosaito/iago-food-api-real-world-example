package br.algaworks.Iago.Food.infrastructure.service.email;

import br.algaworks.Iago.Food.core.email.EmailProperties;
import br.algaworks.Iago.Food.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freeMarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {

        try {
            MimeMessage mimeMessage = criarMimeMessage(mensagem);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {

        String corpo = processarTemplate(mensagem);

        // Mensagem que queremos enviar por email
        // Assunto
        // Destinatário
        // Corpo
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Classe auxiliar para atribuir propriedades dentro do MineMessager
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getRemetente());

        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));

        return mimeMessage;
    }

    /**
     *
     * @param mensagem do email
     * @return conteúdo do email que deverá ser enviado, convertido do html para String
     */
    protected String processarTemplate(Mensagem mensagem) {

        try {
            Template template = freeMarkerConfig.getTemplate(mensagem.getTemplateHtmlCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());

        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template do e-mail", e);
        }
    }
}
