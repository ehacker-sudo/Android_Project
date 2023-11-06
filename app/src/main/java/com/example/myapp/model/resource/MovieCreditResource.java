package com.example.myapp.model.resource;

import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class MovieCreditResource {
    private long id;
    private List<Movie> cast;
    private List<Movie> crew;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Movie> getCast() {
        return cast;
    }

    public void setCast(List<Movie> cast) {
        this.cast = cast;
    }

    public List<Movie> getCrew() {
        return crew;
    }

    public void setCrew(List<Movie> crew) {
        this.crew = crew;
    }
}
