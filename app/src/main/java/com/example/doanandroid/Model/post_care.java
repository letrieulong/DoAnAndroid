package com.example.doanandroid.Model;

public class    post_care {
//    private int resourceId;

    private String content ;
    private String date;
    private String id;
    private String image;
    private int like;
    private String title;
    private int view;

    public post_care(String content, String date, String id, String image, int like, String title, int view) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.image = image;
        this.like = like;
        this.title = title;
        this.view = view;
    }

    public post_care(){

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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
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
}
