package br.algaworks.Iago.Food.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "grupo_permissao",
            joinColumns = @JoinColumn(
                    name = "grupo_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permissao_id"
            )
    )
    private Set<Permissao> permissoes;

    public void adicionarPermissao(Permissao permissao) {
        getPermissoes().add(permissao);
    }

    public void removerPermissao(Permissao permissao) {
        getPermissoes().remove(permissao);
    }
 }
