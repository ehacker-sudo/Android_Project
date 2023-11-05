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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapp.adapter.AlbumAdapter;
import com.example.myapp.adapter.CastAdapter;
import com.example.myapp.adapter.CrewAdapter;
import com.example.myapp.adapter.GenresAdapter;
import com.example.myapp.adapter.TvExtraInfoAdapter;
import com.example.myapp.adapter.TvPagnateAdapter;
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.adapter.VideoAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.film_interface.CollectListener;
import com.example.myapp.film_interface.ExtraInfoListener;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.film_interface.GenresListener;
import com.example.myapp.filter.GenresActivity;
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
                                    Intent intent = new Intent(MovieActivity.this, GenresActivity.class);
                                    intent.putExtra("id",genres.getId());
                                    startActivity(intent);
                                }
                            }
                    ));

            Retrofit.retrofit.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.SliderCallBack(binding));

            Retrofit.retrofit.getTvSerieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvCreditCallBack(getApplicationContext(),binding));

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

                        List<Video> videoList = new ArrayList<>();
                        videoList.add(filmResource.getResults().get(0 + (int)(Math.random() * ( filmResource.getResults().size() - 1 ))));
                        VideoAdapter videoAdapter = new VideoAdapter(getApplicationContext(),videoList);
                        binding.contentFilm.recycleviewVideoMovie.setAdapter(videoAdapter);
                    }
                }

                @Override
                public void onFailure(Call<FilmResource<Video>> call, Throwable t) {

                }
            });

        } else if (getIntent().getStringExtra("media_type").equals("movie")) {
            Retrofit.retrofit.getMovieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.MovieDetailCallBack(getApplicationContext(),binding));

            Retrofit.retrofit.getMovieImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(ApiService.SliderCallBack(binding));

            Retrofit.retrofit.getMovieCredits(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvCreditCallBack(getApplicationContext(),binding));

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
        } else if (getIntent().getStringExtra("media_type").equals("person")) {
//            Retrofit.retrofit.getTvSerieDetails(
//                    getIntent().getIntExtra("series_id",37854),
//                            getIntent().getIntExtra("season_number",1),
//                            getIntent().getIntExtra("id",1),"")
//                    .enqueue();
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