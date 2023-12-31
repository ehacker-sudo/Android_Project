package com.example.myapp.adapter;

import com.example.myapp.model.film.Genres;

import java.util.List;

public class Item {
    private String nameGroup;
    private List<Genres> genresList;

    public Item(String nameGroup, List<Genres> genresList) {
        this.nameGroup = nameGroup;
        this.genresList = genresList;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public List<Genres> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<Genres> genresList) {
        this.genresList = genresList;
    }
}
