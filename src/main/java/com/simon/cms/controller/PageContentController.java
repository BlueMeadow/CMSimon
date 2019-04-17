package com.simon.cms.controller;

import com.simon.cms.dao.dao.CommentaireDAO;
import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Commentaire;
import com.simon.cms.model.Page;
import org.json.JSONObject;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PageContentController {

  @Autowired
  PageDAO pageDAO;

  @Autowired
  CommentaireDAO commDAO;

  @Autowired
  HttpServletRequest request;

//  @Autowired
//  AccessToken token;

  @GetMapping("/{url}")
  public String displayPage(Principal principal, Model model, @PathVariable String url){
    // Contient l'id

    Page p = pageDAO.findPageByUrl(url);
    List<Commentaire> comms = commDAO.getListCommentaireByPage(p);
    model.addAttribute("page", p);
    model.addAttribute("commentaires", comms);
    return "/index";
  }

  @PostMapping("/{url}")
  public String addComment(Model model, Principal principal, @RequestBody String contenu_c, Errors errors, @PathVariable String url) {

    String result;

    //Si erreur -> Renvoyer un 400 bad request
    if (errors.hasErrors()) {
      result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(","));
      return result;
    }

    JSONObject jsonObject = new JSONObject(contenu_c);
    String contenu = jsonObject.getString("contenu_c");

    Page p = pageDAO.findPageByUrl(url);
    KeycloakSecurityContext ksc = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    Commentaire comm = new Commentaire(ksc.getIdToken().getPreferredUsername(), p, Calendar.getInstance().getTime(), contenu);

    commDAO.addCommentaire(comm);

    List<Commentaire> updatedCommentList = commDAO.getListCommentaireByPage(p);

    model.addAttribute("commentaires", updatedCommentList);

    result = "/fragments/pageContent :: comments";

    return result;

  }

}
