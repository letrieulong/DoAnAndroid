package com.example.doanandroid.Model;

import java.io.Serializable;

public class New_Tranning  implements Serializable {
    String id;
    String content_link;
    String title;
    String link;
    String date;
    long view;
    long like;
    boolean isRecruit;

    public New_Tranning(String title) {
        this.title = title;
    }

    public New_Tranning(){

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

    public boolean isRecruit() {
        return isRecruit;
    }

    public void setRecruit(boolean recruit) {
        isRecruit = recruit;
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
