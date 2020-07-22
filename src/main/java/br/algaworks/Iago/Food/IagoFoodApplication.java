package br.algaworks.Iago.Food;

import br.algaworks.Iago.Food.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = {"br.algaworks.Iago.Food"}, repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class IagoFoodApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(IagoFoodApplication.class, args);
	}

}
