package com.simon.cms.dao.dao;

import com.simon.cms.model.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PageDAO {

    int getCountPage();
    List<Page> getPageList();
    List<Page> getVisiblePageList();
    Page addPage(Page p);
    Page updatePage(Page p);
    void changeVisibility(Page p);
    Page findPageById(Long id);
    Page findPageByUrl(String url);
    void removePage(Page p);
    org.springframework.data.domain.Page<Page> findPaginated(Pageable pageable);


}
