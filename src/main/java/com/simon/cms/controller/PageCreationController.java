package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.form.ContentPageDto;
import com.simon.cms.model.Page;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

@Controller
public class PageCreationController {

  @Autowired
  HttpServletRequest request;

  @Autowired
  PageDAO pageDAO;

  @GetMapping("/administration/pageCreation")
  public String showCreationForm(Model model){

    ContentPageDto dto = new ContentPageDto("", "", "");
    model.addAttribute("pageForm", dto);

    return "/administration/pageCreation";
  }

  @PostMapping("/administration/pageCreation")
  public String processCreation(ContentPageDto dto){

    Page p;
    try {
      KeycloakSecurityContext ksc = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
      p = new Page(URLEncoder.encode(dto.getUrl(), StandardCharsets.UTF_8.toString()), dto.getTitre(), dto.getContenu_p(),ksc.getIdToken().getPreferredUsername(), Calendar.getInstance().getTime());
    } catch (UnsupportedEncodingException e) {
      return "/error";
    }
    pageDAO.addPage(p);

    return "redirect:/administration/pageSelection";
  }

}
