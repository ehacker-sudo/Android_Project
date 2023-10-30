package com.example.myapp.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.adapter.MoviePagnateAdapter;
import com.example.myapp.adapter.SearchAdapter;
import com.example.myapp.adapter.TvPagnateAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.databinding.ActivityPaginateBinding;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.resource.FilmResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaginateActivity extends AppCompatActivity {
    private ActivityPaginateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaginateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String collect = getIntent().getStringExtra("collect");

        if (collect.length() < 11){
            binding.toolbarTitle.setText(collect);
        } else {
            String toolbar_title = "";
            String tv_name = collect;
            for (int i = 0; i <= 10; i++) {
                toolbar_title = toolbar_title + tv_name.charAt(i);
            }
            binding.toolbarTitle.setText(toolbar_title + "...");
        }
        if (collect.contains("Các bộ phim")) {
            Retrofit.retrofit.getPopularMovie("vi-Vn", 1)
                    .enqueue(ApiService.PopularMoviePagnateCallBack(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {
                            Intent intent = new Intent(PaginateActivity.this, MovieActivity.class);
                            intent.putExtra("id",movie.getId());
                            intent.putExtra("media_type","movie");
                            startActivity(intent);
                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {

                        }
                    }));
        } else if (collect.contains("Các chương trình truyền hình")) {
            Retrofit.retrofit.getPopularTvSeries("vi-Vn", 1)
                    .enqueue(ApiService.PopularTvPagnateCallBack(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {

                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {
                            Intent intent = new Intent(PaginateActivity.this, MovieActivity.class);
                            intent.putExtra("id",tvSerie.getId());
                            intent.putExtra("media_type","movie");
                            startActivity(intent);
                        }
                    }));
        } else if (collect.contains("Chương trình phồ biến gần đây")) {
            Retrofit.retrofit.getTrendingTvSeries("day", "en")
                    .enqueue(ApiService.PopularTvPagnateCallBack(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {

                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {
                            Intent intent = new Intent(PaginateActivity.this, MovieActivity.class);
                            intent.putExtra("id",tvSerie.getId());
                            intent.putExtra("media_type","movie");
                            startActivity(intent);
                        }
            }));
        } else if (collect.contains("Bộ phim phổ biến gần đây")) {
            Retrofit.retrofit.getTrendingMovies("day","en")
                    .enqueue(ApiService.PopularMoviePagnateCallBack(getApplicationContext(), binding, new FilmClickListener() {
                        @Override
                        public void onClickItemMovie(Movie movie) {
                            Intent intent = new Intent(PaginateActivity.this, MovieActivity.class);
                            intent.putExtra("id",movie.getId());
                            intent.putExtra("media_type","movie");
                            startActivity(intent);
                        }

                        @Override
                        public void onClickItemTvSerie(TvSerie tvSerie) {

                        }
            }));
        } else if (collect.contains("Bộ phim sắp ra mắt")) {
            Retrofit.retrofit.getUpcomingMovies("en",1).enqueue(ApiService.PopularMoviePagnateCallBack(getApplicationContext(), binding, new FilmClickListener() {
                @Override
                public void onClickItemMovie(Movie movie) {
                    Intent intent = new Intent(PaginateActivity.this, MovieActivity.class);
                    intent.putExtra("id",movie.getId());
                    intent.putExtra("media_type","movie");
                    startActivity(intent);
                }

                @Override
                public void onClickItemTvSerie(TvSerie tvSerie) {

                }
            }));
        }

    }

}