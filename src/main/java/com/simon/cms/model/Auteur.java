package com.simon.cms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Auteur {

    @Id
    @GeneratedValue
    private Long auteur_id;
    private String nom;
    private String prenom;
    private String mail;


    public Auteur(String nom, String prenom, String mail) {
        this.nom = nom;
        this.mail = mail;
        this.prenom = prenom;
    }

    public Auteur() {
    }

    public Long getId() {
        return auteur_id;
    }

    public void setId(Long auteur_id) {
        this.auteur_id = auteur_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
