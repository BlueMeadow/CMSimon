package com.simon.cms.DTO;

public class SignUpDto {


  private String pseudo;
  private String mail;
  private String mdp;
  private String nom;
  private String prenom;

  public SignUpDto(){}

  public SignUpDto(String pseudo, String mail, String mdp, String nom, String prenom) {
    this.pseudo = pseudo;
    this.mail = mail;
    this.mdp = mdp;
    this.nom = nom;
    this.prenom = prenom;
  }

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
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
}
