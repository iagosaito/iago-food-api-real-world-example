package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.Cidade;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}

