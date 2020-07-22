package br.algaworks.Iago.Food.api.v1.assembler;

import br.algaworks.Iago.Food.api.v1.controller.PedidoController;
import br.algaworks.Iago.Food.api.v1.links.ApiLinks;
import br.algaworks.Iago.Food.api.v1.model.dto.PedidoResumoModel;
import br.algaworks.Iago.Food.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = modelMapper.map(pedido, PedidoResumoModel.class);

        pedidoResumoModel.add(links.linkToPedidos("pedidos"));

        pedidoResumoModel.getCliente().add(
                links.linkToUsuario(pedidoResumoModel.getCliente().getId())
        );

        pedidoResumoModel.getRestaurante().add(
                links.linkToRestaurante(pedidoResumoModel.getRestaurante().getId())
        );

        return pedidoResumoModel;
    }

    @Override
    public CollectionModel<PedidoResumoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities).add(linkTo(PedidoController.class).withSelfRel());
    }

    //    public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
//        return pedidos.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
}
