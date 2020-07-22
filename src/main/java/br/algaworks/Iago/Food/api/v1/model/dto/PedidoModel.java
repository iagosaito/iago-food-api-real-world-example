package br.algaworks.Iago.Food.api.v1.model.dto;

import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import br.algaworks.Iago.Food.domain.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @ApiModelProperty(value = "Chave UUID do Pedido", example = "d68e5e21-8c11-472d-9e26-32cb296e43a9")
    private String codigo;

    @ApiModelProperty(example = "100.00")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "110.00")
    private BigDecimal valorTotal;

    private StatusPedido status;

    @ApiModelProperty(example = "2020-01-01T00:00:01Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2020-01-01T00:01:01Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2020-01-01T00:02:01Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2020-01-01T00:02:11Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

    private FormaPagamentoModel formaPagamento;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

    public Long getCidadeId() {
        return enderecoEntrega.getCidade().getId();
    }
}
