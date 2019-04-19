package com.simon.cms.dao.impl;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class PageDAOImpl implements PageDAO {

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional(readOnly=true)
    public int getCountPage() {
        String qlString = "SELECT count(page) FROM Page page";
        return (int) em.createQuery(qlString).getSingleResult();
    }

    @Override
    @Transactional
    public List<Page> getPageList() {
        // Ordonnée de la plus récente à la plus ancienne
        String qlString = "SELECT page FROM Page page ORDER BY page.dateEditionP DESC, page.dateCreationP DESC, page.pageId DESC";

        return em.createQuery(qlString).getResultList();
    }

    /*
     * Affiche toutes les pages visibles dans l'ordre de leur publication (antéchronologique)
     */
    @Override
    @Transactional
    public List<Page> getVisiblePageList() {
        String qlString = "SELECT page FROM Page page WHERE page.visible = true ORDER BY page.datePublicationP DESC";
        return em.createQuery(qlString).getResultList();
    }

    @Override
    @Transactional
    public Page addPage(Page p) {
        em.persist(p);
        return p;
    }

    @Override
    @Transactional
    public Page updatePage(Page p) {
        em.merge(p);
        return p;
    }

    @Override
    @Transactional
    public void changeVisibility(Page p) {
        p.setVisible(!p.isVisible());
        // Change la date de publication si la page est devenue visible
        if(p.isVisible()){
            p.setDatePublicationP(Calendar.getInstance().getTime());
        }
        updatePage(p);
    }

    @Override
    @Transactional
    public Page findPageById(Long id) {
        return em.find(Page.class, id);
    }

    @Override
    @Transactional
    public Page findPageByUrl(String url) {
        String qlString = "SELECT page FROM Page page WHERE page.url LIKE '"+url+"'";
        return (Page) em.createQuery(qlString).getSingleResult();
    }

    @Override
    @Transactional
    public void removePage(Page p){
        em.remove(p);
    }

    @Override
    public org.springframework.data.domain.Page<Page> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Page> pages = getPageList();
        List<Page> list;

        if (pages.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pages.size());
            list = pages.subList(startItem, toIndex);
        }

        org.springframework.data.domain.Page<Page> pagePage
            = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), pages.size());

        return pagePage;
    }

}

