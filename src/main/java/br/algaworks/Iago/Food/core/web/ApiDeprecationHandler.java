package br.algaworks.Iago.Food.core.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiDeprecationHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        if (request.getRequestURI().startsWith("/v1/")) {
//            response.setStatus(HttpStatus.GONE.value());
//            return false;
//        }

        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-IagoFood-Deprecated",
                    "Essa versão da API está depreciada e deixará de existir a partir do dia 20/03/2022");
        }

        return true;
    }
}
