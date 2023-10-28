package com.example.myapp.api;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.adapter.CollectionAdapter;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.resource.FilmResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {
    public static Callback<FilmResource<Movie>> PopularMovieCallback(CollectionAdapter.ViewHolder holder, Context context) {
        return new retrofit2.Callback<FilmResource<Movie>>() {
            @Override
            public void onResponse(retrofit2.Call<FilmResource<Movie>> call, retrofit2.Response<FilmResource<Movie>> response) {
                FilmResource<Movie> movieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                holder.recycleviewMovie.setLayoutManager(layoutManager);

                if (movieFilmResource != null) {
                    MovieAdapter movieAdapter = new MovieAdapter(context, movieFilmResource.getResults());
                    holder.recycleviewMovie.setAdapter(movieAdapter);
                } else {
                    MovieAdapter movieAdapter = new MovieAdapter(context, new ArrayList<>());
                    holder.recycleviewMovie.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<FilmResource<Movie>> call, Throwable t) {

            }
        };
    }

    public static Callback<FilmResource<TvSerie>> PopularTvSeriesCallback(CollectionAdapter.ViewHolder holder, Context context) {
        return new Callback<FilmResource<TvSerie>>() {
            @Override
            public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {
                FilmResource<TvSerie> tvSerieFilmResource = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
                holder.recycleviewMovie.setLayoutManager(layoutManager);

                if (tvSerieFilmResource != null) {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,tvSerieFilmResource.getResults());
                    holder.recycleviewMovie.setAdapter(tvSerieAdapter);
                }
                else {
                    TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(context,new ArrayList<>());
                    holder.recycleviewMovie.setAdapter(tvSerieAdapter);
                }
            }

            @Override
            public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

            }
        };
    }
}
