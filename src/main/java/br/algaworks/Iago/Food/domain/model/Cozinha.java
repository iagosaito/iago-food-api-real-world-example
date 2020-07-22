package br.algaworks.Iago.Food.domain.model;

import br.algaworks.Iago.Food.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@JsonRootName("gastronomia")
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

//    @JsonIgnore
//    @JsonProperty("titulo")
    @NotBlank
    @Column(length = 30)
    private String nome; //Tailandesa, Brasileira, Indiana

    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();
}
