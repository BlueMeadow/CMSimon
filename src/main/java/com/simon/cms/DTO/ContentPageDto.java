package com.simon.cms.DTO;

public class ContentPageDto {

    private String url;
    private String titre;
    private String contenuP;
    private String resume;
    //private MultipartFile image;

    public ContentPageDto(String url, String titre, String contenuP, String resume/*, MultipartFile image*/) {
        this.url = url;
        this.titre = titre;
        this.contenuP = contenuP;
        this.resume = resume;
        //this.image = image;
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

    public String getContenuP() {
        return contenuP;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setContenuP(String contenuP) {
        this.contenuP = contenuP;
    }

//    public MultipartFile getImage() {
//        return image;
//    }
//
//    public void setImage(MultipartFile image) {
//        this.image = image;
//    }
}
