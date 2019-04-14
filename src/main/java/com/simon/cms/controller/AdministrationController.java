package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdministrationController {

    @Autowired
    PageDAO pageDAO;

    @GetMapping("/administration")
    public String showAdmin(Principal principal, Model model){


        return "administration";
    }
}
