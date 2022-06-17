package com.example.doanandroid.Model;

public class Infor_All_CNTT {
    String id;
    String title;
    String image;
    String content;
    String date;
    String content_link;

    public Infor_All_CNTT(String id, String title, String image, String content, String date, String content_link) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.date = date;
        this.content_link = content_link;
    }

    public Infor_All_CNTT() {

    }

    public Infor_All_CNTT(String id, String title, String image, String content, String date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.date = date;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
