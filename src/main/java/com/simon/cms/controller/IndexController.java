package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {

    private final PageDAO pageDAO;

    @Autowired
    public IndexController(PageDAO pageDAO) {
        this.pageDAO = pageDAO;
    }


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String showIndex(Model model, Principal principal){

        List<Page> pages = pageDAO.getVisiblePageList();

        model.addAttribute("pages", pages);

        return "index";
    }

}