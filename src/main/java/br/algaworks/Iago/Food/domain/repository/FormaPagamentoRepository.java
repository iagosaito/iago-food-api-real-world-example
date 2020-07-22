package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("Select max(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getDataUltimaAtualizacao();

}

