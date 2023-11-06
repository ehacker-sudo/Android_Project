package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapp.adapter.VideoAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.film_interface.CastListener;
import com.example.myapp.film_interface.CollectListener;
import com.example.myapp.film_interface.ExtraInfoListener;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.film_interface.GenresListener;
import com.example.myapp.film_interface.VideoListener;
import com.example.myapp.filter.GenresActivity;
import com.example.myapp.model.credit.Cast;
import com.example.myapp.model.film.ExtraInfo;
import com.example.myapp.model.film.Genres;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.MovieInfo;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.film.TvSerieInfo;
import com.example.myapp.model.film.Video;
import com.example.myapp.model.resource.CreditsResource;
import com.example.myapp.model.resource.FilmResource;
import com.example.myapp.model.resource.ImageType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Toast.makeText(this, "One piece", Toast.LENGTH_LONG).show();
        setSupportActionBar(binding.toolbar);

        binding.contentFilm.allImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieActivity.this, AlbumActivity.class);
                intent.putExtra("id",getIntent().getIntExtra("id",37854));
                intent.putExtra("media_type",getIntent().getStringExtra("media_type"));
                startActivity(intent);
            }
        });

        if (getIntent().getStringExtra("media_type").equals("tv")) {
            Retrofit.retrofit.getTvSerieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvDetailsCallBack(
                            getApplicationContext(),
                            binding,
                            new ExtraInfoListener() {
                                @Override
                                public void OnClickListener(ExtraInfo extraInfo) {
                                    Intent intent = new Intent(MovieActivity.this, SeasonActivity.class);
                                    intent.putExtra("id",extraInfo.getId());
                                    startActivity(intent);
                                }
                            },
                            new GenresListener() {
                                @Override
                                public void OnClick(Genres genres) {
                                    Intent intent = new Intent(MovieActivity.this, AdvanceSearchActivity.class);
                                    intent.putExtra("media_type","tv");
                                    intent.putExtra("with_genres",Integer.toString(genres.getId()));
                                    startActivity(intent);
                                }
                            }
                    ));

            Retrofit.retrofit.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.SliderCallBack(binding));

            Retrofit.retrofit.getTvSerieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvCreditCallBack(getApplicationContext(), binding
                            , new CastListener() {
                                @Override
                                public void onClick(Cast cast) {
                                    Intent intent = new Intent(MovieActivity.this, CastActivity.class);
                                    intent.putExtra("id",Long.toString(cast.getId()));
                                    startActivity(intent);
                                }
                            }));

            Retrofit.retrofit.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.TvImageCallBack(getApplicationContext(),binding));

            Retrofit.retrofit.getTvSerieRecommendations(getIntent().getIntExtra("id",37854),"en",1)
                    .enqueue(ApiService.TvRecommendCallBack(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {

                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {
                            Intent intent = new Intent(MovieActivity.this, MovieActivity.class);
                            intent.putExtra("id",tvSerie.getId());
                            intent.putExtra("media_type","tv");
                            startActivity(intent);
                        }
                    }));

            Retrofit.retrofit.getVideoTv(getIntent().getIntExtra("id",37854),"en-US").enqueue(new Callback<FilmResource<Video>>() {
                @Override
                public void onResponse(Call<FilmResource<Video>> call, Response<FilmResource<Video>> response) {
                    FilmResource<Video> filmResource = response.body();
                    if (!filmResource.getResults().isEmpty()) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false);
                        binding.contentFilm.recycleviewVideoMovie.setLayoutManager(layoutManager);

                        VideoAdapter videoAdapter = new VideoAdapter(getApplicationContext(),filmResource.getResults());
                        videoAdapter.setVideoListener(new VideoListener() {
                            @Override
                            public void onClick(Video video) {
                                Intent intent = new Intent(MovieActivity.this, VideoActivity.class);
                                intent.putExtra("id",video.getKey());
                                startActivity(intent);
                            }
                        });
                        binding.contentFilm.recycleviewVideoMovie.setAdapter(videoAdapter);
                    }
                }

                @Override
                public void onFailure(Call<FilmResource<Video>> call, Throwable t) {

                }
            });

        } else if (getIntent().getStringExtra("media_type").equals("movie")) {
            Retrofit.retrofit.getMovieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.MovieDetailCallBack(getApplicationContext(), binding,
                            new GenresListener() {
                                @Override
                                public void OnClick(Genres genres) {
                                    Intent intent = new Intent(MovieActivity.this, AdvanceSearchActivity.class);
                                    intent.putExtra("media_type","movie");
                                    intent.putExtra("with_genres",Integer.toString(genres.getId()));
                                    startActivity(intent);
                                }
                            }));

            Retrofit.retrofit.getMovieImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.SliderCallBack(binding));

            Retrofit.retrofit.getMovieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvCreditCallBack(getApplicationContext(), binding,
                            new CastListener() {
                                @Override
                                public void onClick(Cast cast) {
                                    Intent intent = new Intent(MovieActivity.this, CastActivity.class);
                                    intent.putExtra("id",Long.toString(cast.getId()));
                                    startActivity(intent);
                                }
                            }));

            Retrofit.retrofit.getMovieImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.TvImageCallBack(getApplicationContext(),binding));

            Retrofit.retrofit.getMovieRecommendations(getIntent().getIntExtra("id",37854),"",1)
                    .enqueue(ApiService.MovieRecommendCallback(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {
                            Intent intent = new Intent(MovieActivity.this, MovieActivity.class);
                            intent.putExtra("id",movie.getId());
                            intent.putExtra("media_type","movie");
                            startActivity(intent);
                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {

                        }
                    }));
        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}