package com.simon.cms.model;

import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Page {

    @Id
    @GeneratedValue
    private Long pageId;
    private String url;
    private String titre;
    private String resume;
    @Column(columnDefinition="Text")
    private String contenuP;

    private Date dateCreationP;
    @Nullable
    private Date dateEditionP;
    @Nullable
    private Date datePublicationP;
    private boolean visible;
    private String auteur;
//    @Nullable
//    private String pathToImage;


    // nouvelle page
    public Page(String url, String titre, String contenuP, String resume, String auteur, Date dateCreationP/*, String pathToImage*/) {
        this.url = url;
        this.titre = titre;
        this.contenuP = contenuP;
        this.resume = resume;
        this.dateCreationP = dateCreationP;
        this.dateEditionP = null;
        this.visible = false;
        this.auteur = auteur;
//        this.pathToImage = pathToImage;

    }

    public Page() {
    }

    public Long getId() {
        return pageId;
    }

    public void setId(Long page_id) {
        this.pageId = page_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getContenu() {
        return contenuP;
    }

    public void setContenu(String contenu_p) {
        this.contenuP = contenu_p;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDateCreation() {
        return dateCreationP;
    }

    public void setDateCreation(Date date_creation_p) {
        this.dateCreationP = date_creation_p;
    }

    public Date getDateEdition() {
        return dateEditionP;
    }

    public void setDateEdition(Date date_edition_p) {
        this.dateEditionP = date_edition_p;
    }

    @Nullable
    public Date getDatePublication() {
        return datePublicationP;
    }

    public void setDatePublicationP(@Nullable Date datePublicationP) {
        this.datePublicationP = datePublicationP;
    }

//    @Nullable
//    public String getPathToImage() {
//        return pathToImage;
//    }
//
//    public void setPathToImage(@Nullable String pathToImage) {
//        this.pathToImage = pathToImage;
//    }

    // Juste pour éviter d'avoir à utiliser 3 setters dans le controleur et garder le code clair
    public void edit(String titre, String url, String contenu_p, String resume, Date dateEditionP){
        this.titre = titre;
        this.url = url;
        //pour regénérer le résumé
        this.resume = resume;
        //Ne change la date que si la page est visible
        if(visible)
            this.dateEditionP = dateEditionP;
    }

}
