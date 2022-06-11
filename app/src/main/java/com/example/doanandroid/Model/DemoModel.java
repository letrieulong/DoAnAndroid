package com.example.doanandroid.Model;

public class DemoModel {
    String title;
    String date;
    String view;
    int img;
    public DemoModel(String title, String date, String view, int img) {
        this.title = title;
        this.date = date;
        this.view = view;
        this.img = img;
    }

    public DemoModel(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
