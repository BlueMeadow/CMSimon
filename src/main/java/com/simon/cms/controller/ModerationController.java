package com.simon.cms.controller;

import com.simon.cms.dao.dao.CommentaireDAO;
import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Commentaire;
import com.simon.cms.model.Page;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ModerationController {

  @Autowired
  PageDAO pageDAO;

  @Autowired
  CommentaireDAO commDAO;

  @GetMapping("/moderation")
  public String showModerationPage(Model model){

    List<Page> pages = pageDAO.getPageList();
    List<List<Commentaire>> listCommsNonMod = new ArrayList<>();
    List<List<Commentaire>> listCommsMod = new ArrayList<>();

    for(Page p : pages){
      listCommsNonMod.add(commDAO.getListCommentaireNonModeresByPage(p));
      listCommsMod.add(commDAO.getListCommentaireModeresByPage(p));
    }

    model.addAttribute("pages", pages);
    model.addAttribute("commNonModeres", listCommsNonMod);
    model.addAttribute("commModeres", listCommsMod);

    return "/moderation";
  }

  @PostMapping("/moderation/vus")
  public String markAsRead() {

    List<Page> pages = pageDAO.getPageList();

    for(Page p : pages){
      for(Commentaire c : commDAO.getListCommentaireNonModeresByPage(p)){
        commDAO.markAsRead(c);
      }
    }
    return "redirect:/moderation";
  }

  @PostMapping("/moderation")
  public String deleteComment (Model model, @RequestBody String id, Errors errors){
    String result;
    //Si erreur -> Renvoyer un 400 bad request
    if (errors.hasErrors()) {
      result = errors.getAllErrors()
                   .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                   .collect(Collectors.joining(","));
      return result;
    }

    JSONObject jsonObject = new JSONObject(id);
    Long idComm = jsonObject.getLong("id");
    Boolean modere = jsonObject.getBoolean("modere");

    commDAO.removeCommentaire(commDAO.findCommentaireById(idComm));
    List<Page> pages = pageDAO.getPageList();
    List<List<Commentaire>> listComms = new ArrayList<>();

    model.addAttribute("pages", pages);
    if(modere){
      for(Page p : pages){
        listComms.add(commDAO.getListCommentaireModeresByPage(p));
      }
      model.addAttribute("commModeres", listComms);
      return "/fragments/comments :: moderes";
    } else {
      for(Page p : pages){
        listComms.add(commDAO.getListCommentaireNonModeresByPage(p));
      }
      model.addAttribute("commNonModeres", listComms);
      return "fragments/comments :: nonModeres";
    }

  }



}
