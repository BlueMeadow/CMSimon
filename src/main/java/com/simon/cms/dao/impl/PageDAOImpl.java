package com.simon.cms.dao.impl;

import com.simon.cms.dao.dao.PageDAO;
import com.simon.cms.model.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
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
        String qlString = "SELECT page FROM Page page ORDER BY page.date_edition_p DESC, page.date_creation_p DESC, page.page_id DESC";

        return em.createQuery(qlString).getResultList();
    }

    /*
     * Affiche totues les pages visibles dans l'ordre de leur publication (antéchronologique)
     */
    @Override
    public List<Page> getVisiblePageList() {
        String qlString = "SELECT page FROM Page page WHERE page.visible = true ORDER BY page.date_publication_p DESC";
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

    public void changeVisibility(Page p) {
        p.setVisible(!p.isVisible());
        // Change la date de publication si la page est devenue visible
        if(p.isVisible()){
            p.setDate_publication_p(Calendar.getInstance().getTime());
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
        String qlString = "SELECT page FROM Page page WHERE page.url LIKE "+url;
        return (Page) em.createQuery(qlString).getSingleResult();
    }

    @Override
    @Transactional
    public void removePage(Page p){
        em.remove(p);
    }
}

