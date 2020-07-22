package br.algaworks.Iago.Food.jpa;

import br.algaworks.Iago.Food.IagoFoodApplication;
import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {

//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new SpringApplicationBuilder(IagoFoodApplication.class)
//                .web(WebApplicationType.NONE)
//                .run(args);
//
//        RestauranteRepository repository = applicationContext.getBean(RestauranteRepository.class);
//        List<Restaurante> restaurantes = repository.listar();
//
//        for (Restaurante restaurante: restaurantes) {
//            System.out.println("Nome do Restaurante: " + restaurante.getNome() +
//                    "\nNome da Cozinha: " + restaurante.getCozinha().getNome());
//        }
//    }
}
