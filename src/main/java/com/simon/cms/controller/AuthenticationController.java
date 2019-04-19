package com.simon.cms.controller;

import com.simon.cms.DTO.SignUpDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AuthenticationController {

  @GetMapping("/signup")
  public String showSignInPage(Model model){

    SignUpDto suc = new SignUpDto();

    model.addAttribute("signUpForm", suc);
    return "registration";
  }

  @PostMapping("/signup")
  public String processSignIn(SignUpDto dto){

//    Auteur aut = new Auteur(dto.getNom(), dto.getPrenom(), dto.getMail());

//    String hashedMdp = Hashing.sha256().hashString(dto.getMdp(), StandardCharsets.UTF_8).toString();

//    auteurDAO.addAuteur(aut);
//
    return "redirect:/";
  }


  @GetMapping("/login")
  public String login(@RequestParam("redirect") Optional<String> url){

    String urlRedirect = url.orElse("index");

    return "redirect:http://localhost:8080/auth/realms/CMSimon/protocol/openid-connect/auth?client_id=CMSimon-app&response_mode=fragment&response_type=code&login=true&redirect_uri=" +
               "http://localhost:8180/protectedSwitcheroo?redirect="+urlRedirect;

  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request) throws ServletException {
    request.logout();

    return "redirect:/";
  }

  @GetMapping("/protectedSwitcheroo")
  public String protectedSwitcheroo(@RequestParam("redirect") String redirect){
    return "redirect:/"+redirect;
  }

  @GetMapping("/myAccount")
  public String accountManagement(){
    return "redirect:http://localhost:8080/auth/realms/CMSimon/account";
  }
}
