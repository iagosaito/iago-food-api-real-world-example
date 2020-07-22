package br.algaworks.Iago.Food.core.security.authorizationserver;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe para adicionar propriedades customizadas no payload do token
 */
public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {

        if (oAuth2Authentication.getPrincipal() instanceof AuthUser) {
            AuthUser authUser = (AuthUser) oAuth2Authentication.getPrincipal();

            Map<String, Object> info = new HashMap<>();
            info.put("nome_completo", authUser.getFullName());
            info.put("usuario_id", authUser.getUserId());

            DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;

            oAuth2AccessToken.setAdditionalInformation(info);

            return accessToken;
        }

        return accessToken;
    }
}
