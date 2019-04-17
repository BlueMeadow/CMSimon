package com.simon.cms.config;


import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {


  /*  demande au SimpleAuthorityMapper de vérifier que les roles ne sont pas
   *  préfixés par >> ROLE_ <<
   */
  @Autowired
  public void configureGlobal(
      AuthenticationManagerBuilder auth) throws Exception {

    KeycloakAuthenticationProvider keycloakAuthenticationProvider
        = keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
        new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST,
      proxyMode = ScopedProxyMode.TARGET_CLASS)
  public AccessToken getAccessToken() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder
                                        .currentRequestAttributes()).getRequest();
    return ((KeycloakPrincipal) request.getUserPrincipal())
               .getKeycloakSecurityContext().getToken();
  }

  /*
   * Fait en sorte d'utiliser le application.properties
   * de spring boot plutôt que le choix par défaut de keycloak (keycloak.json)
   */
  @Bean
  public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(
        new SessionRegistryImpl());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/administration*").hasRole("admin") //Nécessite le role admin pour accéder à /administration
        .antMatchers("/moderation*").hasAnyRole("admin", "moderator") //Nécessite le role admin ou modérateur pour accéder à /moderation
//        .antMatchers(HttpMethod.POST, "/*").authenticated() // Les gens peuvent faire des posts (commentaires)
        .anyRequest().permitAll();  // le reste des egns a accès en lecture
  }

}