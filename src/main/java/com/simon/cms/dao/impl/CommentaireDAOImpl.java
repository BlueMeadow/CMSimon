package com.simon.cms.dao.impl;

import com.simon.cms.dao.dao.CommentaireDAO;
import com.simon.cms.model.Commentaire;
import com.simon.cms.model.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class CommentaireDAOImpl implements CommentaireDAO {

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional(readOnly=true)
    public int getCountCommentaire() {
        String qlString = "SELECT count(commentaire) FROM Commentaire commentaire";
        return (int) em.createQuery(qlString).getSingleResult();

    }

    @Override
    @Transactional
    public List<Commentaire> getListCommentaire() {
        String qlString = "SELECT commentaire FROM Commentaire commentaire";
        return em.createQuery(qlString).getResultList();
    }

    @Override
    @Transactional
    public List<Commentaire> getListCommentaireByPage(Page p) {
        String qlString = "SELECT commentaire FROM Commentaire commentaire WHERE " +
                              "commentaire.page = '"+p.getId()+"' ORDER BY commentaire.datePublicationC DESC";
        return em.createQuery(qlString).getResultList();
    }

    @Override
    @Transactional
    public List<Commentaire> getListCommentaireModeresByPage(Page p) {
        String qlString = "SELECT commentaire FROM Commentaire commentaire WHERE " +
                          "commentaire.page = '"+p.getId()+"' AND commentaire.modere = true ORDER BY commentaire.datePublicationC";
        return em.createQuery(qlString).getResultList();

    }

    @Override
    @Transactional
    public List<Commentaire> getListCommentaireNonModeresByPage(Page p) {
        String qlString = "SELECT commentaire FROM Commentaire commentaire WHERE " +
                              "commentaire.page = '"+p.getId()+"' AND commentaire.modere = false ORDER BY commentaire.datePublicationC";
        return em.createQuery(qlString).getResultList();

    }

    @Override
    @Transactional
    public Commentaire addCommentaire(Commentaire c) {
        em.persist(c);
        return c;
    }

    @Override
    @Transactional
    public Commentaire updateCommentaire(Commentaire c) {
        em.merge(c);
        return c;
    }

    @Override
    @Transactional
    public Commentaire findCommentaireById(Long id) {
        return em.find(Commentaire.class, id);
    }

    @Override
    @Transactional
    public void removeCommentaire(Commentaire c){
        em.remove(c);
    }

    @Override
    @Transactional
    public void markAsRead(Commentaire c){
        c.setModere(true);
        em.merge(c);
    }


}
