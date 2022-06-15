package com.example.doanandroid.Model;

public class Mechanical {
    String id;
    String title;
    String link;
    String date;
    String views;
    String size;
    String content_link;
    ContentLink list_Link;

    public Mechanical() {
    }

    public Mechanical(String id, String title, String link, String date, String views, String size, String content_link) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.date = date;
        this.views = views;
        this.size = size;
        this.content_link = content_link;
    }

    public Mechanical(String id, String title, String link, String date, String views, String size, String content_link, ContentLink list_Link) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.date = date;
        this.views = views;
        this.size = size;
        this.content_link = content_link;
        this.list_Link = list_Link;
    }

    public String getContent_link() {
        return content_link;
    }

    public ContentLink getList_Link() {
        return list_Link;
    }

    public void setList_Link(ContentLink list_Link) {
        this.list_Link = list_Link;
    }

    public void setContent_link(String content_link) {
        this.content_link = content_link;
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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
