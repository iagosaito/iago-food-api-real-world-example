package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.*;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoModel;
import br.algaworks.Iago.Food.core.security.AlgaSecurity;
import br.algaworks.Iago.Food.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = modelMapper.map(pedido, PedidoModel.class);

        pedidoModel.add(
                links.linkToPedido(pedidoModel.getCodigo())
        );

        pedidoModel.add(
                links.linkToPedidos("pedidos")
        );

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(links.linkToConfirmacaoPedido(
                        pedidoModel.getCodigo(), "confirmar")
                );
            }

            if (pedido.podeSerCancelado()) {
                pedidoModel.add(
                        links.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar")
                );
            }

            if (pedido.podeSerEntregue()) {
                pedidoModel.add(
                        links.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar")
                );
            }
        }



        pedidoModel.getRestaurante().add(
                links.linkToRestaurante(pedidoModel.getRestaurante().getId())
        );

        pedidoModel.getCliente().add(
                links.linkToUsuario(pedidoModel.getCliente().getId())
        );

        pedidoModel.getFormaPagamento().add(
                links.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId())
        );

        pedidoModel.getEnderecoEntrega().getCidade().add(
                links.linkToCidade(pedidoModel.getCidadeId())
        );

        pedidoModel.getItens().forEach(item -> {
            item.add(
                links.linkToProduto(pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto")
            );
        });

        return pedidoModel;
    }

    @Override
    public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(linkTo(PedidoController.class).withSelfRel());
    }

}
