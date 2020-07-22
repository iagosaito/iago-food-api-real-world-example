package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.model.Pedido;
import br.algaworks.Iago.Food.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.confirmar();

        // Tivemos que chamar o método save para fazer com que o evento registrado
        // dentro do método pedido.confirmar() seja disparado
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = pedidoService.buscar(codigoPedido);
        pedido.cancelar();

        // Tivemos que chamar o método save para fazer com que o evento registrado
        // dentro do método pedido.confirmar() seja disperado
        pedidoRepository.save(pedido);
    }

}
