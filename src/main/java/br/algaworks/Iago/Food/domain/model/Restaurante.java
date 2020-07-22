package br.algaworks.Iago.Food.domain.model;

import br.algaworks.Iago.Food.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria = "Frete Grátis"
)
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 40, nullable = false)
    private String nome;

//    @TaxaFrete
//    @PositiveOrZero
//    @Multiplo(numero = 10)
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne//(fetch = FetchType.LAZY)
//    @JsonIgnore
//    @JsonIgnoreProperties("hibernateLazyInitializer")

    // O @NotNull em cozinha indica que o objeto
    // não pode ser nulo. Não os atributos dentro
    // do objeto.
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    // Propriedade da entidade é de um tipo que vai ser incorporada a classe.
    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante") @ToString.Exclude
    private List<Produto> produtos;

    @Column(nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column
    private Boolean aberto = Boolean.FALSE;

//    @JsonIgnore
    @ToString.Exclude
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(
            name = "restaurante_forma_pagamento",

            //Referência da própria tabela
            joinColumns = @JoinColumn(
                    name = "restaurante_id"
            ),

            //Referência da tabela inversa
            inverseJoinColumns = @JoinColumn(
                    name = "forma_pagamento_id"
            )
    )
    private Set<FormaPagamento> formasPagamentos = new HashSet<>();

    @ManyToMany @ToString.Exclude
    @JoinTable(
            name = "restaurante_usuario_responsavel",

            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")

    )
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().remove(formaPagamento);
    }

    public boolean adicionarResponsavel(Usuario usuario) {
        return getResponsaveis().add(usuario);
    }

    public boolean removerResponsavel(Usuario usuario) {
        return getResponsaveis().remove(usuario);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamentos().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }

    public boolean isAberto() {
        return this.aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public boolean isInativo() {
        return !isAtivo();
    }

    public boolean aberturaPermitida() {
        return isAtivo() && isFechado();
    }

    public boolean ativacaoPermitida() {
        return isInativo();
    }

    public boolean inativacaoPermitida() {
        return isAtivo();
    }

    public boolean fechamentoPermitido() {
        return isAberto();
    }
}
