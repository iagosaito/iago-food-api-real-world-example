package br.algaworks.Iago.Food.domain.event;

import br.algaworks.Iago.Food.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {
    private Pedido pedido;
}
