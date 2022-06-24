package com.example.doanandroid.Model;

public class post_like {
    private String content_link;
     private String data;
     private String id;
    private String image;
    private String link;
    private String title;

    public post_like(){

    }
    public String getContent_link() {
        return content_link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public post_like(String content_link, String data, String id, String image, String link, String title) {
        this.content_link = content_link;
        this.data = data;
        this.id = id;
        this.image = image;
        this.link = link;
        this.title = title;
    }
}
