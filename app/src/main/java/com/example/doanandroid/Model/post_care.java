package com.example.doanandroid.Model;

public class post_care {
    private int resourceId;
    private String title;
    private String date;

    public post_care(int resourceId, String title, String date) {
        this.resourceId = resourceId;
        this.title = title;
        this.date = date;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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
}
