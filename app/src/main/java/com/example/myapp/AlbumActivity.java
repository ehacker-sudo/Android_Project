package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myapp.adapter.AlbumAdapter;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.SecondAlbumAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityAlbumBinding;
import com.example.myapp.databinding.ActivityVideoBinding;
import com.example.myapp.model.film.MovieInfo;
import com.example.myapp.model.film.TvSerieInfo;
import com.example.myapp.model.resource.ImageType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {
    private ActivityAlbumBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlbumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbarTitle.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("media_type").equals("tv")) {
            Retrofit.retrofit.getTvSerieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<TvSerieInfo>() {
                        @Override
                        public void onResponse(Call<TvSerieInfo> call, Response<TvSerieInfo> response) {
                            TvSerieInfo tvSerieInfo = response.body();
                            if (tvSerieInfo.getName().length() < 11){
                                binding.toolbarTitle.setText(tvSerieInfo.getName());
                            } else {
                                String toolbar_title = "";
                                String tv_name = tvSerieInfo.getName();
                                for (int i = 0; i <= 10; i++) {
                                    toolbar_title = toolbar_title + tv_name.charAt(i);
                                }
                                binding.toolbarTitle.setText(toolbar_title + "...");
                            }
                        }

                        @Override
                        public void onFailure(Call<TvSerieInfo> call, Throwable t) {

                        }
                    });

            Retrofit.retrofit.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(new Callback<ImageType>() {
                        @Override
                        public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                            ImageType imageType = response.body();


                            GridLayoutManager gridLayoutManager = new GridLayoutManager(AlbumActivity.this, 2);
                            binding.recycleViewAlbum.setLayoutManager(gridLayoutManager);

                            SecondAlbumAdapter secondAlbumAdapter = new SecondAlbumAdapter(AlbumActivity.this,imageType.getBackdrops());
                            binding.recycleViewAlbum.setAdapter(secondAlbumAdapter);
                        }

                        @Override
                        public void onFailure(Call<ImageType> call, Throwable t) {

                        }
                    });
        } else if (getIntent().getStringExtra("media_type").equals("movie")) {
            Retrofit.retrofit.getMovieDetails(getIntent().getIntExtra("id",37854),"en")
                    .enqueue(new Callback<MovieInfo>() {
                        @Override
                        public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                            MovieInfo movieInfo = response.body();
                            if (movieInfo.getTitle().length() < 11){
                                binding.toolbarTitle.setText(movieInfo.getTitle());
                            } else {
                                String toolbar_title = "";
                                String tv_name = movieInfo.getTitle();
                                for (int i = 0; i <= 10; i++) {
                                    toolbar_title = toolbar_title + tv_name.charAt(i);
                                }
                                binding.toolbarTitle.setText(toolbar_title + "...");
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieInfo> call, Throwable t) {

                        }
                    });


            Retrofit.retrofit.getMovieImages(getIntent().getIntExtra("id",37854),"")
                    .enqueue(new Callback<ImageType>() {
                        @Override
                        public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                            ImageType imageType = response.body();


                            GridLayoutManager gridLayoutManager = new GridLayoutManager(AlbumActivity.this, 2);
                            binding.recycleViewAlbum.setLayoutManager(gridLayoutManager);

                            SecondAlbumAdapter secondAlbumAdapter = new SecondAlbumAdapter(AlbumActivity.this,imageType.getBackdrops());
                            binding.recycleViewAlbum.setAdapter(secondAlbumAdapter);
                        }

                        @Override
                        public void onFailure(Call<ImageType> call, Throwable t) {

                        }
                    });
        }

    }
}