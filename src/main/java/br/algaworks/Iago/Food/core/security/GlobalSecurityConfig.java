package br.algaworks.Iago.Food.core.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdvice;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

public class GlobalSecurityConfig extends GlobalMethodSecurityConfiguration {

}
