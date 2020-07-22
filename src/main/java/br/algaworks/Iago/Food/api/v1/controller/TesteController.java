package br.algaworks.Iago.Food.api.v1.controller;

import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(String nome){
        return cozinhaRepository.findAllByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFret(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorNome(String nome, Long idCozinha){
        return restauranteRepository.consultarPorNome(nome, idCozinha);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantesPrimeiroPorNome(String nome){
        return restauranteRepository.findFirstByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restaurantesTop2PorNome(String nome){
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/exists")
    public boolean cozinhaExists(String nome){
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/restaurantes/count")
    public int countByCozinhaId(Long idCozinha){
        return restauranteRepository.countByCozinhaId(idCozinha);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> comFreteGratis(String nome){
//        var comFreteGratis = new RestauranteComFreteGratisSpec();
//        var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);

        return restauranteRepository.findComFreteGratis(nome);
    }
}

