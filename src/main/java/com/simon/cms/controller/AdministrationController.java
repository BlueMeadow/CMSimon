package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrationController {

    @Autowired
    PageDAO pageDAO;

    @GetMapping("/administration")
    public String showAdmin(){
        return "administration";
    }

}
