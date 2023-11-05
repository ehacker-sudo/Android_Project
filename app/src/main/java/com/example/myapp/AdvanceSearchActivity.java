package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityAdvanceSearchBinding;
import com.example.myapp.databinding.ActivitySettingsBinding;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.resource.FilmResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvanceSearchActivity extends AppCompatActivity {

    ActivityAdvanceSearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvanceSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvanceSearchActivity.this, SeasonActivity.class);
                startActivity(intent);
            }
        });
        String with_genres = getIntent().getStringExtra("with_genres");
        if (getIntent().getStringExtra("media_type").equals("tv")) {
            Retrofit.retrofit.getDiscoverTv("en", 1,false,"popularity.desc",with_genres).enqueue(new Callback<FilmResource<TvSerie>>() {
                @Override
                public void onResponse(Call<FilmResource<TvSerie>> call, Response<FilmResource<TvSerie>> response) {
                    FilmResource<TvSerie> tvSerieFilmResource = response.body();

                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    binding.recycleviewSearchFilter.setLayoutManager(layoutManager);

                    if (tvSerieFilmResource != null) {
                        TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(getApplicationContext(),tvSerieFilmResource.getResults());
                        tvSerieAdapter.setFilmClickListener(new FilmClickListener() {
                            @Override
                            public void onClickItemMovie(Movie movie) {
                            }

                            @Override
                            public void onClickItemTvSerie(TvSerie tvSerie) {
                                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                                intent.putExtra("id",tvSerie.getId());
                                intent.putExtra("media_type","tv");
                                startActivity(intent);
                            }
                        });
                        binding.recycleviewSearchFilter.setAdapter(tvSerieAdapter);
                    }
                    else {
                        TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(getApplicationContext(),new ArrayList<>());
                        binding.recycleviewSearchFilter.setAdapter(tvSerieAdapter);
                    }
                }

                @Override
                public void onFailure(Call<FilmResource<TvSerie>> call, Throwable t) {

                }
            });
        } else if (getIntent().getStringExtra("media_type").equals("movie")) {
            Retrofit.retrofit.getDiscoverMovie("en", 1,false,"popularity.desc",with_genres).enqueue(new Callback<FilmResource<Movie>>() {
                @Override
                public void onResponse(Call<FilmResource<Movie>> call, Response<FilmResource<Movie>> response) {
                    FilmResource<Movie> movieFilmResource = response.body();

                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    binding.recycleviewSearchFilter.setLayoutManager(layoutManager);

                    if (movieFilmResource != null) {
                        MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), movieFilmResource.getResults());
                        movieAdapter.setFilmClickListener(new FilmClickListener() {
                            @Override
                            public void onClickItemMovie(Movie movie) {
                                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                                intent.putExtra("id",movie.getId());
                                intent.putExtra("media_type","movie");
                                startActivity(intent);
                            }

                            @Override
                            public void onClickItemTvSerie(TvSerie tvSerie) {

                            }
                        });
                        binding.recycleviewSearchFilter.setAdapter(movieAdapter);
                    } else {
                        MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), new ArrayList<>());
                        binding.recycleviewSearchFilter.setAdapter(movieAdapter);
                    }
                }

                @Override
                public void onFailure(Call<FilmResource<Movie>> call, Throwable t) {

                }
            });
        }

    }
}