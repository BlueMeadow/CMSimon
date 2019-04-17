package com.simon.cms.controller;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PageSelectionController {

  @Autowired
  PageDAO pageDAO;

  @GetMapping("/administration/pageSelection")
  public String showPageSelection(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size){

    // Sur la page passée en paramètre ou sur la première
    int currentPage = page.orElse(1);

    // 8 pages du site par page de la pagination (pour une grille de 9 avec le bouton nouvelle page)
    int pageSize = size.orElse(8);


    org.springframework.data.domain.Page<Page> pagePage = pageDAO.findPaginated(PageRequest.of(currentPage-1, pageSize));

    model.addAttribute("pagination", pagePage);

    int totalPages = pagePage.getTotalPages();
    if(totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "/administration/pageSelection";
  }

  @GetMapping("/administration/pageSelection/visibilite/{page_id}")
  public String changeVisibility(@PathVariable Long page_id){

    Page p = pageDAO.findPageById(page_id);
    pageDAO.changeVisibility(p);

    return "redirect:/administration/pageSelection";
  }
}
