package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.form.ContentPageDto;
import com.simon.cms.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;

@Controller
public class PageEditionController {

    @Autowired
    PageDAO pageDAO;

    @GetMapping("/administration/pageEdition/{page_id}")
    public String showEditionForm(Model model, @PathVariable Long page_id){

        Page p = pageDAO.findPageById(page_id);
        model.addAttribute("page", p);

        ContentPageDto dto = new ContentPageDto(p.getUrl(), p.getTitre(), p.getContenu());
        model.addAttribute("pageForm", dto);

        return "/administration/pageEdition";
    }

    @PostMapping("/administration/pageEdition/{page_id}")
    public String processEdition(ContentPageDto dto, @PathVariable Long page_id){
            Page p = pageDAO.findPageById(page_id);
            p.edit(dto.getTitre(), dto.getUrl(), dto.getContenu_p(), Calendar.getInstance().getTime());
            pageDAO.updatePage(p);
        return "redirect:/administration/pageSelection";
    }

}
