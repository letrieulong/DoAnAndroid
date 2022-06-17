package com.example.doanandroid.Model;

public class New_Tranning {
    String id;
    String content_link;
    String title;
    String link;
    String date;

    public New_Tranning(String title) {
        this.title = title;
    }

    public New_Tranning(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
