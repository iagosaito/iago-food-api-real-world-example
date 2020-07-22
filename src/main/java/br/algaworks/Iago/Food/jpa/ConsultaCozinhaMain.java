package br.algaworks.Iago.Food.jpa;

import br.algaworks.Iago.Food.IagoFoodApplication;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new SpringApplicationBuilder(IagoFoodApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//
//        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
//        List<Cozinha> cozinhas = cadastroCozinha.listar();
//
//        for (Cozinha cozinha: cozinhas) {
//            System.out.println(cozinha.getNome());
//        }
//    }
}
