package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}

