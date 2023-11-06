package com.example.myapp.model.resource;

import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class TvCreditResource {
    private long id;
    private List<TvSerie> cast;
    private List<TvSerie> crew;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<TvSerie> getCast() {
        return cast;
    }

    public void setCast(List<TvSerie> cast) {
        this.cast = cast;
    }

    public List<TvSerie> getCrew() {
        return crew;
    }

    public void setCrew(List<TvSerie> crew) {
        this.crew = crew;
    }
}
