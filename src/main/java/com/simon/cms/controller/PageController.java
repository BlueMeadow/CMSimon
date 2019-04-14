package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

public class PageController {

  @Autowired
  PageDAO pageDAO;

  @GetMapping("/{url}")
  public String displayPage(Principal principal, Model model, @PathVariable String url){
    // Contient l'id
    String userName = principal.getName();

    if (principal instanceof KeycloakPrincipal) {
      @SuppressWarnings("unchecked")
      KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) principal;

      // Récupère le pseudo
      userName = kp.getKeycloakSecurityContext().getToken().getName();
    }


    Page p = pageDAO.findPageByUrl(url);
    model.addAttribute("page", p);
    model.addAttribute("author", userName);


    return "/";

  }

}
