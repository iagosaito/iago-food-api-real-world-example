package br.algaworks.Iago.Food.domain.repository;

import br.algaworks.Iago.Food.domain.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
