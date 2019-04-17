package com.simon.cms.dao.dao;

import com.simon.cms.model.Commentaire;
import com.simon.cms.model.Page;

import java.util.List;

public interface CommentaireDAO {

    int getCountCommentaire();
    List<Commentaire> getListCommentaire();
    List<Commentaire> getListCommentaireByPage(Page p);
    Commentaire addCommentaire(Commentaire c);
    Commentaire updateCommentaire(Commentaire c);
    Commentaire findCommentaireById(Long id);
    void removeCommentaire(Commentaire c);
    List<Commentaire> getListCommentaireModeresByPage(Page p);
    List<Commentaire> getListCommentaireNonModeresByPage(Page p);
    void markAsRead(Commentaire c);
}
