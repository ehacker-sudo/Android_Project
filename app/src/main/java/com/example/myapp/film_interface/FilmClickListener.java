package com.example.myapp.film_interface;

import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;

public interface FilmClickListener {
    public void onClickItemMovie(Movie movie);
    public void onClickItemTvSerie(TvSerie tvSerie);
}
