package com.example.doanandroid.Model;

public class Policy {
    String id;
    String date;
    String image;
    String link;
    String content_link;
    String title;

    public Policy() {

    }

    public Policy(String id, String date, String image, String link, String content_link, String title) {
        this.id = id;
        this.date = date;
        this.image = image;
        this.link = link;
        this.content_link = content_link;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
