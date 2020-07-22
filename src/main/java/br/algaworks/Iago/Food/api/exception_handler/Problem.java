package br.algaworks.Iago.Food.api.exception_handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400")
    private Integer status;

    // URI que identifica o tipo do problema
    @ApiModelProperty(example = "https://iagofood.com/dados-invalidos")
    private String type;

    //Problema legível a humanos
    @ApiModelProperty(example = "Dados Inválidos")
    private String title;

    //Problema mais detalhado
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String detail;

    //Mensagem para o usuário
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente")
    private String userMessage;

    @ApiModelProperty(example = "2020-01-01T00:00:00000Z", value = "Data e Hora ISO")
    private OffsetDateTime timestamp;

    @ApiModelProperty("Objetos ou campo que geraram o erro (Opcional)")
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "Preço")
        private String name;

        @ApiModelProperty(example = "O Preço é obrigatório")
        private String userMessage;
    }

}
