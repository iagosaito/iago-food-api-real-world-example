package br.algaworks.Iago.Food;

import br.algaworks.Iago.Food.domain.model.Cozinha;
import br.algaworks.Iago.Food.domain.repository.CozinhaRepository;
import br.algaworks.Iago.Food.domain.service.CozinhaService;
import br.algaworks.Iago.Food.util.DatabaseCleaner;
import br.algaworks.Iago.Food.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaIT {

    public static final int COZINHA_ID_INEXISTENTE = 99999;

    @Autowired
    private CozinhaService cozinhaService;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private Cozinha cozinhaAmericana;

    private int quantidadeCozinhas;

    private String jsonCorretoCozinhaChinesa;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json"
        );

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinha() {

        given()
            .accept(ContentType.JSON)
        .when()
            .get() // Tipo de requisição
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        given()
            .pathParam("cozinhaId", 2)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}") // Tipo de requisição
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
        .when()
                .get("/{cozinhaId}") // Tipo de requisição
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinha() {

        given()
            .accept(ContentType.JSON)
        .when()
            .get() // Tipo de requisição
        .then()
            .body("", hasSize(quantidadeCozinhas));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {

        given()
            .body(jsonCorretoCozinhaChinesa)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    private void prepararDados() {
        Cozinha c1 = new Cozinha();
        c1.setNome("Tailandesa");
        cozinhaRepository.save(c1);

        cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

        quantidadeCozinhas = (int) cozinhaRepository.count();
    }


}
//    @Test
//    public void deveRetornarId_QuandoCadastrarCozinha() {
//        // Código divido em 3 partes: Cenário, Ação e Validação
//        // Implementação de um caminho feliz
//        // -------------------------------------------------------
//
//        // Cenário
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome("Chinesa");
//
//        // Ação
//        novaCozinha = cozinhaService.salvar(novaCozinha);
//
//        // Validação
//        assertNotNull(novaCozinha);
//        assertNotNull(novaCozinha.getId());
//    }
//
//    @Test
//    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//        Cozinha novaCozinha = new Cozinha();
//        novaCozinha.setNome(null);
//
//        Assertions.assertThrows(ConstraintViolationException.class, () ->
//                cozinhaService.salvar(novaCozinha));
//    }
//
//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//        Long id = 1L;
//
//        Assertions.assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.excluir(id));
//    }
//
//    @Test
//    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
//        Long id = 9000L;
//
//        Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> cozinhaService.excluir(id));
//    }

