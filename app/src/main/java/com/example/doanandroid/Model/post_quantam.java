package com.example.doanandroid.Model;

public class post_quantam {
    private String content_link;
    private String data;
    private String id;
    private int like;
    private String link;
    private String title;
    private int view;

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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public post_quantam(String content_link, String data, String id, int like, String link, String title, int view) {
        this.content_link = content_link;
        this.data = data;
        this.id = id;
        this.like = like;
        this.link = link;
        this.title = title;
        this.view = view;
    }

    public post_quantam(){

    }
}
