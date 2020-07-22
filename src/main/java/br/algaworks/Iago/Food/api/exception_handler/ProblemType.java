package br.algaworks.Iago.Food.api.exception_handler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_DE_NEGOCIO("/erro-de-negocio", "Erro de negócio"),
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensível"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro Inválido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
    ACESSO_NEGADO("/acesso-negado", "Acesso Negado");

    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://iagofood.com.br" + path;
        this.title = title;
    }
}
