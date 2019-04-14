package com.simon.cms.controller;

import com.google.common.hash.Hashing;
import com.simon.cms.dao.dao.AuteurDAO;
import com.simon.cms.form.SignUpDto;
import com.simon.cms.model.Auteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.StandardCharsets;

@Controller
public class SignUpController {

  @Autowired
  AuteurDAO auteurDAO;

  @GetMapping("/signup")
  public String showSignInPage(Model model){

    SignUpDto suc = new SignUpDto();

    model.addAttribute("signUpForm", suc);
    return "signup";
  }

  @PostMapping("/signup")
  public String processSignIn(SignUpDto dto){

    Auteur aut = new Auteur(dto.getNom(), dto.getPrenom(), dto.getMail());

    String hashedMdp = Hashing.sha256().hashString(dto.getMdp(), StandardCharsets.UTF_8).toString();

    auteurDAO.addAuteur(aut);

    return "redirect:/";
  }
}
