package com.adobe.aem.guides.wknd.core.models;

import java.util.Date;

public class Article {

    String pathArticle;
    String titleArticle;
    Date dateArticle;
    //String imgArticle;

    public Article(String pathArticle, String titleArticle, Date dateArticle) {
        this.pathArticle = pathArticle;
        this.titleArticle = titleArticle;
        this.dateArticle = dateArticle;
        //this.imgArticle = imgArticle;
    }

    public String getPathArticle() {
        return pathArticle;
    }

    public String getTitleArticle() {
        return titleArticle;
    }

    public Date getDateArticle() {
        return dateArticle;
    }

    //public String getImgArticle() {
       // return imgArticle;
    //}

}
