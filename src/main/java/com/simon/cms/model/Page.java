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
    private Long page_id;
    private String url;
    private String titre;
    private String resume;
    @Column(length = 65535,columnDefinition="Text")
    private String contenu_p;
    private Date date_creation_p;
    @Nullable
    private Date date_edition_p;
    @Nullable
    private Date date_publication_p;
    private boolean visible;
    
    private String auteur;


    // nouvelle page
    public Page(String url, String titre, String contenu_p, String auteur, Date date_creation_p) {
        this.url = url;
        this.titre = titre;
        this.contenu_p = contenu_p;
        this.date_creation_p = date_creation_p;
        this.date_edition_p = null;
        generateResume();
        this.visible = false;
        this.auteur = auteur;
    }

    public Page() {
    }

    public Long getId() {
        return page_id;
    }

    public void setId(Long page_id) {
        this.page_id = page_id;
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

    public String getContenu() {
        return contenu_p;
    }

    public void setContenu(String contenu_p) {
        this.contenu_p = contenu_p;
        generateResume();
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
        return date_creation_p;
    }

    public void setDateCreation(Date date_creation_p) {
        this.date_creation_p = date_creation_p;
    }

    public Date getDateEdition() {
        return date_edition_p;
    }

    public void setDateEdition(Date date_edition_p) {
        this.date_edition_p = date_edition_p;
    }

    @Nullable
    public Date getDatePublication() {
        return date_publication_p;
    }

    public void setDate_publication_p(@Nullable Date date_publication_p) {
        this.date_publication_p = date_publication_p;
    }

    // Juste pour éviter d'avoir à utiliser 3 setters dans le controleur et garder le code clair
    public void edit(String titre, String url, String contenu_p, Date date_edition_p){
        this.titre = titre;
        this.url = url;
        //pour regénérer le résumé
        setContenu(contenu_p);
        //Ne change la date que si la page est visible
        if(visible)
            this.date_edition_p = date_edition_p;
    }

    private void generateResume(){
        if(contenu_p.length() < 255){
            this.resume = contenu_p;
        } else {
            this.resume = contenu_p.substring(0, 251) + "...";
        }
    }



}
