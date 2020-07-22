package br.algaworks.Iago.Food.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * Configuração dos detalhes dos clientes (Aplicações frontend)
     *
     * Para um cliente requisitar um TOKEN do Authorization server
     * ele precisará se autenticar.
     *
     * Para isso ele deverá passar o ID -> "algafood-web", e
     * a senha "1234"
     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("iagofood-web") // Identificação do Cliente.
//                .secret(passwordEncoder.encode("1234"))
//                .authorizedGrantTypes("password", "refresh_token") // Fluxo Password (AuthorizationServer com Password Credentials)
//                .scopes("WRITE", "READ")
//                .accessTokenValiditySeconds(10000)
//                .refreshTokenValiditySeconds(20000)
//
//            .and()
//                .withClient("foodanalytics")
//                .secret(passwordEncoder.encode("food1234"))
//                .authorizedGrantTypes("authorization_code") // Fluxo authorization code
//                .scopes("WRITE", "READ")
//                .redirectUris("http://aplicacao-cliente") // URI de redirecionamento
//
//                /*
//                http://localhost:8087/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://aplicacao-cliente
//                 */
//
//            .and()
//                .withClient("webadmin")
//                .authorizedGrantTypes("implicit") // Não funciona com refresh token
//                .scopes("WRITE", "READ")
//                .redirectUris("http://aplicacao-cliente") // URI de redirecionamento
//
//            .and()
//                .withClient("faturamento") // Identificação do Cliente.
//                .secret(passwordEncoder.encode("faturamento1234"))
//                .authorizedGrantTypes("client_credentials") // Cliente sem frontend, roda apenas no background
//                .scopes("WRITE", "READ")
//                .accessTokenValiditySeconds(10000)
//
//            .and()
//                .withClient("checktoken")
//                .secret(passwordEncoder.encode("check123"));
//    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAutenticated() significa que para checar o token é preciso autenticar o cliente
        //.checkTokenAccess("isAuthenticated()"); // Acesso ao endpoint de check token /oauth/check_token
        security.checkTokenAccess("permitAll()")
            .tokenKeyAccess("permitAll()")
            .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(),
                jwtAccessTokenConverter()));

        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenGranter(tokenGranter(endpoints))
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()));
//                .tokenStore(redisTokenStore());
                //.reuseRefreshTokens(false) // Gera um refresh token diferente a requisição
        ;
    }

    // Método para permite aprovação granolar dos scopos no fluxo Authorization Server
    private ApprovalStore approvalStore(TokenStore tokenStore) {
        var approvalStore = new TokenApprovalStore();

        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    // Converte as informações de um usuário logado em JWT e vice versa
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey("fashdfbahdbfahsdbfhasbdfabsdfjhbashjdfbhasdbfhbasdhfbasdjhfbh");

        var jksResource = new ClassPathResource(jwtKeyStoreProperties.getPath());
        var keyStorePassword = jwtKeyStoreProperties.getPassword();
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

        var keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource, keyStorePassword.toCharArray());

        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair);

        return jwtAccessTokenConverter;
    }

//    private TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

}
