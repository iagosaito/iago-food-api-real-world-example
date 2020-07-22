package br.algaworks.Iago.Food.domain.model;

import br.algaworks.Iago.Food.domain.event.PedidoCanceladoEvent;
import br.algaworks.Iago.Food.domain.event.PedidoConfirmadoEvent;
import br.algaworks.Iago.Food.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    private String codigo;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "data_entrega")
    private OffsetDateTime dataEntrega;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    public void calcularValorTotal() {

        getItens().forEach(ItemPedido::calcularPrecoTotal);

        this.subtotal = getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao())
            );
        }

        this.status = novoStatus;
    }

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
        registerEvent(new PedidoConfirmadoEvent(this));
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
        registerEvent(new PedidoCanceladoEvent(this));
    }

    public boolean podeSerConfirmado() {
        return getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerCancelado() {
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    public boolean podeSerEntregue() {
        return getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    @PrePersist
    private void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }

}
