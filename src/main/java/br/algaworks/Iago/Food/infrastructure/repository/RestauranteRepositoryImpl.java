package br.algaworks.Iago.Food.infrastructure.repository;

import br.algaworks.Iago.Food.domain.model.Restaurante;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepository;
import br.algaworks.Iago.Food.domain.repository.RestauranteRepositoryQueries;
import br.algaworks.Iago.Food.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal){

        /*
            Fábrica para construir elementos de consulta
         */
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        /*
            Construtor de cláusulas. Criando um construtor de cláusulas de restaurantes
         */
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class); // = from Restaurante
        // from Restaurante = Root é a raiz, ou seja, o Restaurante

        /*
            Como se fosse um filtro de cada pedaço de uma cláusula Where.

            Root.get("nome") -> Ache para mim o atributo nome dentro da raiz (Restaurante)
            e crie um predicado (filtro) com o parâmetro nome
         */

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)){
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        // Converter uma Lista de Coleção do Java para um Vetor de Predicados.
        // Dessa forma ele retorna uma instância de um Array preenchido com novo Predicate.
        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();
//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)){
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null){
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null){
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        /*
//            Consulta tipada
//         */
//        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
////                .setParameter("nome", "%" + nome + "%")
////                .setParameter("taxaInicial", taxaFreteFinal)
////                .setParameter("taxaFinal", taxaFreteFinal)
////                .getResultList();
//
//        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(
                RestauranteSpecs.comFreteGratis()
                        .and(RestauranteSpecs.comNomeSemelhante(nome))
        );
    }
}
