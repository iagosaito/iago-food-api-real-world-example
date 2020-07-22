package br.algaworks.Iago.Food.domain.service;

import br.algaworks.Iago.Food.domain.exception.NegocioException;
import br.algaworks.Iago.Food.domain.exception.PedidoNaoEncontradoException;
import br.algaworks.Iago.Food.domain.model.*;
import br.algaworks.Iago.Food.domain.repository.PedidoRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmissaoPedidoService {

    private static final String MSG_FORMA_PGTO_RESTAURANTE_N_RELACIONADA = "A Forma de Pagamento de id %d não está " +
            "disponível para o Restaurante de id %d";

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido buscar(String codigo) {
        return pedidoRepository.findByCodigo(codigo).orElseThrow(() ->
                new PedidoNaoEncontradoException(codigo));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItensPedido(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    private void validarItensPedido(Pedido pedido) {
        Long restauranteId = pedido.getRestaurante().getId();

        pedido.getItens().forEach(itemPedido -> {

            Long produtoId = itemPedido.getProduto().getId();

            Produto produto = produtoService.buscar(restauranteId, produtoId);

            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setPrecoUnitario(produto.getPreco());
        });
    }

    private void validarPedido(Pedido pedido) {
        Long restauranteId = pedido.getRestaurante().getId();
        Long formaPagamentoId = pedido.getFormaPagamento().getId();
        Long cidadeId = pedido.getEnderecoEntrega().getCidade().getId();
        Long usuarioId = pedido.getCliente().getId();

        Usuario usuario = usuarioService.buscar(usuarioId);
        Cidade cidade = cidadeService.buscar(cidadeId);
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format(MSG_FORMA_PGTO_RESTAURANTE_N_RELACIONADA, formaPagamentoId, restauranteId));
        }

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(usuario);
        pedido.getEnderecoEntrega().setCidade(cidade);
    }
}
