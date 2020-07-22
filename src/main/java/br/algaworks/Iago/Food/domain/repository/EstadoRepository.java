package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.Estado;
import br.algaworks.Iago.Food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
}

