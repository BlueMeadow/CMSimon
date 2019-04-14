package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.form.ContentPageDto;
import com.simon.cms.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Calendar;

@Controller
public class PageCreationController {

  @Autowired
  PageDAO pageDAO;

  @GetMapping("administration/newPage/")
  public String showEditionForm(Model model){

    ContentPageDto dto = new ContentPageDto("", "", "");
    model.addAttribute("pageForm", dto);

    return "administration/newPage";
  }

  @PostMapping("administration/newPage")
  public String processCreation(Principal principal, Model model, ContentPageDto dto){

    Page p;
    try {
      p = new Page(URLEncoder.encode(dto.getUrl(), StandardCharsets.UTF_8.toString()), dto.getTitre(), dto.getContenu_p(), principal.getName(), Calendar.getInstance().getTime());
    } catch (UnsupportedEncodingException e) {
      return "/error";
    }
    pageDAO.addPage(p);

    return "redirect:/administration";
  }
}
