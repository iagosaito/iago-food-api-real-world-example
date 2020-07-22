package br.algaworks.Iago.Food.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .and()
            .authorizeRequests()
                .antMatchers("oauth/**").authenticated()
            .and()
            .csrf().disable()
//            .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
//                .antMatchers(HttpMethod.PUT,"/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
//                .antMatchers(HttpMethod.PUT,"/v1/cozinhas/**").authenticated()
//                .anyRequest().denyAll()
//            .and()
            .cors()
            .and()
//            .oauth2ResourceServer().opaqueToken(); // Configura Resource Server no prjeto
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAutenticationConverter = new JwtAuthenticationConverter();
        jwtAutenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                authorities = Collections.emptyList();
            }

            var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);

            grantedAuthorities.addAll(authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));

            return grantedAuthorities;
        });


        return jwtAutenticationConverter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        var secretKey = new SecretKeySpec(
//                "fashdfbahdbfahsdbfhasbdfabsdfjhbashjdfbhasdbfhbasdhfbasdjhfbh".getBytes(),
//                "HmacSHA256");
//
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic()
//                .and()
//                    .authorizeRequests()
//                        .antMatchers("/v1/cozinhas/**").permitAll() // Permite chamadas na API sem autenticação
//                        .anyRequest().authenticated() // Autoriza qualquer requisição autenticada
//
//                .and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Aplicação sem Cookie (Sem estado)
//
//                .and()
//                    .csrf().disable();
//    }
