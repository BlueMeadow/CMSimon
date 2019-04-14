package com.simon.cms.dao.impl;

import com.simon.cms.dao.dao.CommentaireDAO;
import com.simon.cms.model.Commentaire;
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
        try{
            return (int) em.createQuery(qlString).getSingleResult();
        }catch(Exception e){
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Commentaire> getListCommentaire() {
        String qlString = "SELECT commentaire FROM Commentaire commentaire";
        try{
            return em.createQuery(qlString).getResultList();
        }catch(Exception e){
        }
        return null;
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
}
