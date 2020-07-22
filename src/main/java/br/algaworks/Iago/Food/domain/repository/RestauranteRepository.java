package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long idCozinha);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")

    /*
        Este método utiliza uma Named Query que está na pasta META-INF
        dentro do arquivo orm.xml
     */
    List<Restaurante> consultarPorNome(String nome, @Param("id") Long idCozinha);

    Optional<Restaurante> findFirstByNomeContaining(String nome);

    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);

    List<Restaurante> findByCozinhaId(String idCozinha);

    Optional<Restaurante> findByNome(String nome);

    boolean existsResponsavel(Long restauranteId, Long usuarioId);

//    @Query("FROM Restaurante r JOIN r.cozinha INNER JOIN FETCH r.formasPagamentos")
//    List<Restaurante> findAll();

    /*
        Em vez de criar uma consulta, o Spring vai ver que existe uma implementação
        customizada automaticamente, e vinculará o método com a implementação automaticamente.
     */

}

