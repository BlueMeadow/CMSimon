package com.simon.cms.dao.impl;

import com.simon.cms.dao.dao.AuteurDAO;
import com.simon.cms.model.Auteur;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class AuteurDAOImpl implements AuteurDAO {

    @PersistenceContext
    public EntityManager em;

    @Override
    @Transactional(readOnly=true)
    public int getCountAuteur() {
        String qlString = "SELECT count(auteur) FROM Auteur auteur";
        try{
            return (int) em.createQuery(qlString).getSingleResult();
        }catch(Exception e){
        }
        return 0;
    }

    @Override
    @Transactional
    public List<Auteur> getListAuteur() {
        String qlString = "SELECT auteur FROM Auteur auteur";
        try{
            return em.createQuery(qlString).getResultList();
        }catch(Exception e){
        }
        return null;
    }

    @Override
    @Transactional
    public Auteur addAuteur(Auteur a) {
        em.persist(a);
        return a;
    }

    @Override
    @Transactional
    public Auteur updateAuteur(Auteur a) {
        em.merge(a);
        return a;
    }

    @Override
    @Transactional
    public Auteur findAuteurById(Long id) {
        return em.find(Auteur.class, id);
    }

    @Override
    @Transactional
    public void removeAuteur(Auteur a){
        em.remove(a);
    }
}
