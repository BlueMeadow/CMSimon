package com.simon.cms.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue
    private Long commId;

    private String auteur;

    @ManyToOne
    @JoinColumn(name = "pageIdC")
    private Page page;

    private Date datePublicationC;

    @Column(length = 65535,columnDefinition="Text")
    private String contenuC;

    //A été vu par un modérateur / administrateur sur la page de modération
    private boolean modere;

    public Commentaire() {
    }

    public Commentaire(String auteur, Page page, Date datePublicationC, String contenuC) {
        this.auteur = auteur;
        this.page = page;
        this.datePublicationC = datePublicationC;
        this.contenuC = contenuC;
        this.modere = false;
    }

    public Long getId() {
        return commId;
    }

    public void setId(Long commId) {
        this.commId = commId;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Date getDate() {
        return datePublicationC;
    }

    public void setDate(Date date) {
        this.datePublicationC = date;
    }

    public String getContenu() {
        return contenuC;
    }

    public void setContenu(String contenu) {
        this.contenuC = contenu;
    }

    public boolean isModere() {
        return modere;
    }

    public void setModere(boolean modere) {
        this.modere = modere;
    }
}
