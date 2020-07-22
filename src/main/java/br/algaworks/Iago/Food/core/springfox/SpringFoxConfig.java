package br.algaworks.Iago.Food.core.springfox;

import br.algaworks.Iago.Food.api.v1.controller.TesteController;
import br.algaworks.Iago.Food.api.v1.openapi.model.*;
import br.algaworks.Iago.Food.api.exception_handler.Problem;
import br.algaworks.Iago.Food.api.v1.model.UsuarioModel;
import br.algaworks.Iago.Food.api.v1.model.dto.*;
import br.algaworks.Iago.Food.api.v2.model.dto.CidadeModelV2;
import br.algaworks.Iago.Food.api.v2.model.dto.CozinhaModelV2;
import br.algaworks.Iago.Food.api.v2.openapi.model.CidadesModelOpenApiV2;
import br.algaworks.Iago.Food.api.v2.openapi.model.CozinhasModelOpenApiV2;
import br.algaworks.Iago.Food.domain.filter.PedidoFilter;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocketV1() {
        var typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.algaworks.Iago.Food.api"))
                .paths(PathSelectors.ant("/v1/**")) // Filtro para versionar a documentação
                .build()

                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())

                // Cria a representação de um Model que não está mapeado
                .additionalModels(typeResolver.resolve(Problem.class))
                .additionalModels(typeResolver.resolve(PedidoFilter.class))

                .apiInfo(apiInfoV1())
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)

                // Remove um parâmetro dos endpoints
                .ignoredParameterTypes(ServletWebRequest.class)
                .ignoredParameterTypes(URL.class)
                .ignoredParameterTypes(URLStreamHandler.class)
                .ignoredParameterTypes(URI.class)
                .ignoredParameterTypes(Resource.class)
                .ignoredParameterTypes(File.class)
                .ignoredParameterTypes(InputStream.class)
                .ignoredParameterTypes(TesteController.class)

                // Adiciona parâmetros implícitos em todos os EndPoints
//                .globalOperationParameters(Arrays.asList(
//                        new ParameterBuilder()
//                            .name("campos")
//                            .description("Nomes das propriedades para filtrar nas respostas, separados por vírgula")
//                            .parameterType("query")
//                            .modelRef(new ModelRef("string"))
//                        .build()
//                ))

                // Substitui um Page<CozinhaModel> num CozinhaModelOpenApi
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CozinhaModel.class), CozinhasModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, PedidoResumoModel.class), PedidosResumoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CidadeModel.class), CidadesModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, EstadoModel.class), EstadosModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class), FormasPagamentoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GrupoModel.class), GruposModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissaoModel.class), GruposModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProdutoModel.class), ProdutosModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class), RestaurantesBasicoModelOpenApi.class))

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UsuarioModel.class), UsuariosModelOpenApi.class))

                .tags(
                        new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os Grupos"),
                        new Tag("Cozinhas", "Gerencia as Cozinhas"),
                        new Tag("Formas de Pagamento", "Gerencia as Formas de Pagamento"),
                        new Tag("Pedidos", "Gerencia os Pedidos"),
                        new Tag("Restaurantes", "Gerencia os Restaurantes"),
                        new Tag("Estados", "Gerencia os Estados"),
                        new Tag("Produtos", "Gerencia os Proutos"),
                        new Tag("Usuários", "Gerencia os Usuários"),
                        new Tag("Estatísticas", "Consultas agrupadas do sistema"),
                        new Tag("Permissões", "Consulta as Permissões")
                )

                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

//    @Bean
//    public Docket apiDocketV2() {
//        var typeResolver = new TypeResolver();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("V2")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("br.algaworks.Iago.Food.api"))
//                .paths(PathSelectors.ant("/v2/**")) // Filtro para versionar a documentação
//                .build()
//
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
//                .globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
//                .globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
//                .globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//
//                // Cria a representação de um Model que não está mapeado
//                .additionalModels(typeResolver.resolve(Problem.class))
//                .additionalModels(typeResolver.resolve(PedidoFilter.class))
//
//                .apiInfo(apiInfoV2())
//                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
//                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
//
//                // Remove um parâmetro dos endpoints
//                .ignoredParameterTypes(ServletWebRequest.class)
//                .ignoredParameterTypes(URL.class)
//                .ignoredParameterTypes(URLStreamHandler.class)
//                .ignoredParameterTypes(URI.class)
//                .ignoredParameterTypes(Resource.class)
//                .ignoredParameterTypes(File.class)
//                .ignoredParameterTypes(InputStream.class)
//                .ignoredParameterTypes(TesteController.class)
//
//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(PagedModel.class, CozinhaModelV2.class), CozinhasModelOpenApiV2.class
//                ))
//
//                .alternateTypeRules(AlternateTypeRules.newRule(
//                        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class), CidadesModelOpenApiV2.class
//                ))
//
//
//                .tags(
//                        new Tag("Cidades", "Gerencia as cidades"),
//                        new Tag("Cozinhas", "Gerencia as Cozinhas")
//                );
//    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno do servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Requisição recusada porque o corpo está em um formato não suportado")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Requisição inválida (erro do cliente)")
                        .responseModel(new ModelRef("Problema"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Erro interno no servidor")
                        .responseModel(new ModelRef("Problema"))
                        .build()
        );
    }

    private ApiInfo apiInfoV1() {
        return new ApiInfoBuilder()
                .title("IagoFood API (Depreciada)")
                .description("API aberta para clientes e restaurantes.<br> " +
                        "<strong>Essa versão da API está depreciada e deixará de existir a partir do dia 20/03/2022.")
                .version("1")
                .contact(new Contact("IagoFood", "https://www.iagofoo.com", "iagofood1999@gmail.com"))
                .build();
    }

//    private ApiInfo apiInfoV2() {
//        return new ApiInfoBuilder()
//                .title("IagoFood API")
//                .description("API aberta para clientes e restaurantes")
//                .version("2")
//                .contact(new Contact("IagoFood", "https://www.iagofoo.com", "iagofood1999@gmail.com"))
//                .build();
//    }

    // Descreve as técnicas de segurança da API
    private SecurityScheme securityScheme() {
        return new OAuthBuilder()
                .name("Iagofood")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    private SecurityContext securityContext() {
        SecurityReference securityReference = SecurityReference.builder()
                .reference("Iagofood")
                .scopes(scopes().toArray(new AuthorizationScope[0]))
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(PathSelectors.any())
                .build();
    }

    // Grant Type disponibilizado para documentação
    private List<GrantType> grantTypes() {
        return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
    }

    private List<AuthorizationScope> scopes() {
        return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
                new AuthorizationScope("WRITE", "Acesso de escrita"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
