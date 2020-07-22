package br.algaworks.Iago.Food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column
    private Long id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String senha;

    @CreationTimestamp
    @Column
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(
                    name = "usuario_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "grupo_id"
            )
    )
    private Set<Grupo> grupos;

    public void adicionarGrupo(Grupo grupo) {
        getGrupos().add(grupo);
    }

    public void removerGrupo(Grupo grupo) {
        getGrupos().remove(grupo);
    }

//    public boolean senhaCoincideCom(String senha) {
//        return getSenha().equals(senha);
//    }

    public boolean isNovo() {
        return getId() == null;
    }


}
