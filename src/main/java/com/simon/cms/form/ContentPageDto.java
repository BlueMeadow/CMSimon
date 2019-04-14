package com.simon.cms.form;

public class ContentPageDto {

    private String url;
    private String titre;
    private String contenu_p;

    public ContentPageDto(String url, String titre, String contenu_p) {
        this.url = url;
        this.titre = titre;
        this.contenu_p = contenu_p;
    }

    public ContentPageDto() {
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

    public String getContenu_p() {
        return contenu_p;
    }

    public void setContenu_p(String contenu_p) {
        this.contenu_p = contenu_p;
    }
}
