package com.example.myapp.model.resource;

import com.example.myapp.model.film.Image;

import java.util.List;

public class ImageType {
    private int id;
    private List<Image> backdrops;
    private List<Image> logos;
    private List<Image> posters;
    private List<Image> stills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Image> getLogos() {
        return logos;
    }

    public void setLogos(List<Image> logos) {
        this.logos = logos;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }

    public List<Image> getStills() {
        return stills;
    }

    public void setStills(List<Image> stills) {
        this.stills = stills;
    }
}
