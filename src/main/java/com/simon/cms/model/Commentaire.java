package com.simon.cms.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
public class Commentaire {

    @Id
    @GeneratedValue
    private Long comm_id;

    @OneToOne
    @JoinColumn(name = "auteur_id_c")
    private Auteur auteur;

    @ManyToOne
    @JoinColumn(name = "page_id_c")
    private Page page;

    private Date date_publication_c;

    @Column(length = 65535,columnDefinition="Text")
    private String contenu_c;

    public Commentaire() {
    }

    public Commentaire(Auteur auteur, Page page, Date date_publication_c, String contenu_c) {
        this.auteur = auteur;
        this.page = page;
        this.date_publication_c = date_publication_c;
        this.contenu_c = contenu_c;
    }

    public Long getId() {
        return comm_id;
    }

    public void setId(Long comm_id) {
        this.comm_id = comm_id;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Date getDate() {
        return date_publication_c;
    }

    public void setDate(Date date_c) {
        this.date_publication_c = date_c;
    }

    public String getContenu() {
        return contenu_c;
    }

    public void setContenu(String contenu_c) {
        this.contenu_c = contenu_c;
    }
}
