package com.example.doanandroid.Model;

public class Tuition {
    String id;
    String title;
    String content_link;
    String link;

    public Tuition() {
    }

    public Tuition(String id, String title, String content_link, String link) {
        this.id = id;
        this.title = title;
        this.content_link = content_link;
        this.link = link;
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

    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
