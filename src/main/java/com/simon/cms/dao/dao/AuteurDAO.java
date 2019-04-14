package com.simon.cms.dao.dao;

import com.simon.cms.model.Auteur;

import java.util.List;

public interface AuteurDAO {
    int getCountAuteur();
    List<Auteur> getListAuteur();
    Auteur addAuteur(Auteur a);
    Auteur updateAuteur(Auteur a);
    Auteur findAuteurById(Long id);
    void removeAuteur(Auteur a);
    
}
