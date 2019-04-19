package com.simon.cms.controller;

import com.simon.cms.dao.dao.CommentaireDAO;
import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Commentaire;
import com.simon.cms.model.Page;
import org.json.JSONObject;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PageContentController {

  private final
  PageDAO pageDAO;

  private final
  CommentaireDAO commDAO;

  @Autowired
  public PageContentController(PageDAO pageDAO, CommentaireDAO commDAO) {
    this.pageDAO = pageDAO;
    this.commDAO = commDAO;
  }


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
  public String addComment(Model model, HttpServletRequest request, @RequestBody String contenuC, Errors errors, @PathVariable String url) {

    String result;

    //Si erreur -> Renvoyer un 400 bad request
    if (errors.hasErrors()) {
      result = errors.getAllErrors()
                        .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(","));
      return result;
    }

    Map m = model.asMap();

    m.forEach((k,v) -> System.out.println(k + " - " + v));

    JSONObject jsonObject = new JSONObject(contenuC);
    String contenu = jsonObject.getString("contenuC");

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
