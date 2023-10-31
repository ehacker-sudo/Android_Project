package com.example.myapp.model.film;

public class ExtraInfo {
    private int id;
    private String name;
    private String detail;
    private String title;

    public ExtraInfo(int id, String name, String detail, String title) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
