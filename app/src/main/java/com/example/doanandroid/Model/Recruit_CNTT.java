package com.example.doanandroid.Model;

import java.io.Serializable;

public class Recruit_CNTT implements Serializable {
    String id;
    String title;
    String content;
    String date;
    String image;
    long view;
    long like;


    public Recruit_CNTT() {
    }

    public Recruit_CNTT(String id, String title, String content, String date, String image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.image = image;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getLike() {
        return like;
    }

    public void setLike(long like) {
        this.like = like;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
