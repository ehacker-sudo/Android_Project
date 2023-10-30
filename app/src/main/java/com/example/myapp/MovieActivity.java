package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.MovieInfo;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.film.TvSerieInfo;
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

        Toast.makeText(this, "asasasasasasasa", Toast.LENGTH_LONG).show();
        setSupportActionBar(binding.toolbar);

        if (getIntent().getStringExtra("media_type").equals("tv")) {
            Retrofit.retrofit.getTvSerieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(ApiService.TvDetailsCallBack(getApplicationContext(),binding));

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