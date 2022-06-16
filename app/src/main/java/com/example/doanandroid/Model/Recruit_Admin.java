package com.example.doanandroid.Model;

public class Recruit_Admin {
    String id;
    String title;
    String content;
    String date;
    String link;
    String image;

    public Recruit_Admin() {
    }

    public Recruit_Admin(String id, String title, String content, String date, String link, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.link = link;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
