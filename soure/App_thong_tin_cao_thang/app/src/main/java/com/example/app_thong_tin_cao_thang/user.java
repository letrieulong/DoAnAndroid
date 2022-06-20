package com.example.app_thong_tin_cao_thang;

public class user {
    public user(int resourceId, String name, String view, String data) {
        this.resourceId = resourceId;
        this.name = name;
        this.view = view;
        this.data = data;
    }

    private int resourceId;
    private String name;
    private String view;
    private String data;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


