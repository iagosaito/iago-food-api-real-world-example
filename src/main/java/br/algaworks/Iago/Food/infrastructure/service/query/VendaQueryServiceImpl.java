package br.algaworks.Iago.Food.infrastructure.service.query;

import br.algaworks.Iago.Food.domain.filter.VendaDiariaFilter;
import br.algaworks.Iago.Food.domain.model.Pedido;
import br.algaworks.Iago.Food.domain.model.StatusPedido;
import br.algaworks.Iago.Food.domain.model.dto.VendaDiaria;
import br.algaworks.Iago.Food.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.*;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * select date(p.data_criacao) as data_criacao,
     *        count(p.id) as quantidade_vendas,
     *        sum(p.valor_total) as total_faturado
     *
     * from pedido
     *
     * group by date(p.data_criacao)
     */
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {

        var builder = entityManager.getCriteriaBuilder();

        // Declaramos que especificamos uma VendaDiaria como retorno.
        // OBS: Não é a cláusula from
        var query = builder.createQuery(VendaDiaria.class);

        // Especificando a cláusula from
        var root = query.from(Pedido.class);

        var convertTzDataCriacao
                = builder.function("convert_tz", Date.class, root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateDataCriacao =
                builder.function("date", Date.class, convertTzDataCriacao);

        // Construa Venda Diária a partir da seleção, ou seja o retorno
        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal"))
        );

        var predicates = new ArrayList<>();

        if (filter.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restauranteId"), filter.getRestauranteId()));
        }

        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
        }

        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));


        // Status deve ser sempre = Entregue ou Confirmado
        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }
}
