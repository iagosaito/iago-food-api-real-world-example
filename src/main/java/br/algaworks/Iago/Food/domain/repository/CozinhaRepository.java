package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

//    List<Cozinha> findQualquerCoisaByNome(String nome);

    List<Cozinha> findAllByNomeContaining(String nome);

    boolean existsByNome(String nome);
}