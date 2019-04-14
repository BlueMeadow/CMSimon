package com.simon.cms.dao.dao;

import com.simon.cms.model.Commentaire;

import java.util.List;

public interface CommentaireDAO {

    int getCountCommentaire();
    List<Commentaire> getListCommentaire();
    Commentaire addCommentaire(Commentaire c);
    Commentaire updateCommentaire(Commentaire c);
    Commentaire findCommentaireById(Long id);
    void removeCommentaire(Commentaire c);
    
}
