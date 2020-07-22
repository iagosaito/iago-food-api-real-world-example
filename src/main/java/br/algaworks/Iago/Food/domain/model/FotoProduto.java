package br.algaworks.Iago.Food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "produto_id")
    private Long id;

    private String nomeArquivo;

    private String descricao;

    private Long tamanho;

    private String contentType;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Indica que o produto vai ser mapeamento pelo ID da classe
    private Produto produto;

    public Long getRestauranteId() {
        if (getProduto() == null)
            return null;

        return getProduto().getRestaurante().getId();
    }
}
