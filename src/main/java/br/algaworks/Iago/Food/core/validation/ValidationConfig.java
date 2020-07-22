package br.algaworks.Iago.Food.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    /**
     * Método responsável por fazer a validação da especificação
     * Bean Validator com o Validator do Spring
     *
     * @param messageSource -> Responsável por ler messages.properties e
     *                      fazer a resolução de mensagens
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
