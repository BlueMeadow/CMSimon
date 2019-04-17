package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    PageDAO pageDAO;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndex(Model model){

        List<Page> pages = pageDAO.getVisiblePageList();

        model.addAttribute("pages", pages);

        return "index";
    }

    @GetMapping("/logout")
    public String logout() throws ServletException {
        request.logout();

        return "redirect:/";
    }
}