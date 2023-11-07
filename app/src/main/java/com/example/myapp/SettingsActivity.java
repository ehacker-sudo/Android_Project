package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.adapter.Item;
import com.example.myapp.adapter.SecondCollectionAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivitySettingsBinding;
import com.example.myapp.film_interface.SecondCollectListener;
import com.example.myapp.model.film.Genres;
import com.example.myapp.model.film.MovieInfo;
import com.example.myapp.model.film.TvSerieInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity implements SecondCollectListener {
    private ActivitySettingsBinding binding;
    private boolean isVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        SecondCollectionAdapter collectionAdapter = new SecondCollectionAdapter(this,listItem());
        collectionAdapter.setCollectListener(this);
        recyclerView.setAdapter(collectionAdapter);

    }

    private List<Item> listItem() {
        List<Item> itemList = new ArrayList<>();
        List<Genres> group1 = new ArrayList<>();
        group1.add(new Genres("Phim ảnh"));
        group1.add(new Genres("Phim truyền hình"));

        itemList.add(new Item("Kiểu phim",group1));

        return itemList;
    }
    @Override
    public void OnClickListener(SecondCollectionAdapter.ViewHolder viewHolder) {
        if (isVisible){
            viewHolder.tvDesc.setVisibility(View.GONE);
            viewHolder.arrowUp.setVisibility(View.GONE);
            viewHolder.arrowDown.setVisibility(View.VISIBLE);
            isVisible = false;
        } else {
            viewHolder.tvDesc.setVisibility(View.VISIBLE);
            viewHolder.arrowUp.setVisibility(View.VISIBLE);
            viewHolder.arrowDown.setVisibility(View.GONE);
            isVisible = true;
        }
    }

    @Override
    public void OnCheckBoxListener(List<Genres> stringList) {
        if (stringList.size() == 0 || stringList.size() == 2) {
            List<Item> itemList = new ArrayList<>();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            binding.recycleViewGenres.setLayoutManager(linearLayoutManager);
            SecondCollectionAdapter collectionAdapter = new SecondCollectionAdapter(this,itemList);
            collectionAdapter.setCollectListener(SettingsActivity.this);
            binding.recycleViewGenres.setAdapter(collectionAdapter);
            binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                    intent.putExtra("media_type","movie");
                    startActivity(intent);
                }
            });
        } else {
            if (stringList.get(0).getName().equals("Phim truyền hình")) {
                binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                        intent.putExtra("media_type","tv");
                        intent.putExtra("with_genres","");
                        startActivity(intent);
                    }
                });

                Retrofit.retrofit.getGenresTvList().enqueue(new Callback<TvSerieInfo>() {
                    @Override
                    public void onResponse(Call<TvSerieInfo> call, Response<TvSerieInfo> response) {

                        TvSerieInfo tvSerieInfo = response.body();
                        Toast.makeText(SettingsActivity.this,tvSerieInfo.getGenres().get(0).getName(),Toast.LENGTH_LONG).show();
                        List<Item> itemList = new ArrayList<>();
                        List<Genres> stringList_Genres = new ArrayList<>();
                        for (int i = 0; i < tvSerieInfo.getGenres().size(); i++) {
                            stringList_Genres.add(new Genres(tvSerieInfo.getGenres().get(i).getId(),tvSerieInfo.getGenres().get(i).getName()));
                        }
                        itemList.add(new Item("Thể loại",stringList_Genres));

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                        binding.recycleViewGenres.setLayoutManager(linearLayoutManager);
                        SecondCollectionAdapter collectionAdapter = new SecondCollectionAdapter(SettingsActivity.this,itemList);
                        collectionAdapter.setCollectListener(new SecondCollectListener() {
                            public String with_genres = "";
                            @Override
                            public void OnClickListener(SecondCollectionAdapter.ViewHolder viewHolder) {
                                if (isVisible){
                                    viewHolder.tvDesc.setVisibility(View.GONE);
                                    viewHolder.arrowUp.setVisibility(View.GONE);
                                    viewHolder.arrowDown.setVisibility(View.VISIBLE);
                                    isVisible = false;
                                } else {
                                    viewHolder.tvDesc.setVisibility(View.VISIBLE);
                                    viewHolder.arrowUp.setVisibility(View.VISIBLE);
                                    viewHolder.arrowDown.setVisibility(View.GONE);
                                    isVisible = true;
                                }
                            }

                            @Override
                            public void OnCheckBoxListener(List<Genres> stringList) {
                                if (!with_genres.equals("")) {
                                    with_genres = "";
                                }
                                if (stringList.size() != 0) {
                                    for (int i = 0; i < stringList.size() - 1; i++) {
                                        with_genres = with_genres + stringList.get(i).getId() + " | ";
                                    }
                                    with_genres = with_genres + stringList.get(stringList.size() - 1).getId();
                                }
                                binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                                        intent.putExtra("media_type","tv");
                                        intent.putExtra("with_genres",with_genres);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                        binding.recycleViewGenres.setAdapter(collectionAdapter);
                    }

                    @Override
                    public void onFailure(Call<TvSerieInfo> call, Throwable t) {

                    }
                });
            } else {
                binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                        intent.putExtra("media_type","movie");
                        intent.putExtra("with_genres","");
                        startActivity(intent);
                    }
                });
                Retrofit.retrofit.getGenresMovieList().enqueue(new Callback<MovieInfo>() {
                    @Override
                    public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                        MovieInfo movieInfo = response.body();
                        List<Item> itemList = new ArrayList<>();
                        List<Genres> stringList_Genres = new ArrayList<>();
                        for (int i = 0; i < movieInfo.getGenres().size(); i++) {
                            stringList_Genres.add(new Genres(movieInfo.getGenres().get(i).getId(),movieInfo.getGenres().get(i).getName()));
                        }
                        itemList.add(new Item("Thể loại",stringList_Genres));

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SettingsActivity.this);
                        binding.recycleViewGenres.setLayoutManager(linearLayoutManager);
                        SecondCollectionAdapter collectionAdapter = new SecondCollectionAdapter(SettingsActivity.this,itemList);
                        collectionAdapter.setCollectListener(new SecondCollectListener() {
                            public String with_genres = "";
                            @Override
                            public void OnClickListener(SecondCollectionAdapter.ViewHolder viewHolder) {
                                if (isVisible){
                                    viewHolder.tvDesc.setVisibility(View.GONE);
                                    viewHolder.arrowUp.setVisibility(View.GONE);
                                    viewHolder.arrowDown.setVisibility(View.VISIBLE);
                                    isVisible = false;
                                } else {
                                    viewHolder.tvDesc.setVisibility(View.VISIBLE);
                                    viewHolder.arrowUp.setVisibility(View.VISIBLE);
                                    viewHolder.arrowDown.setVisibility(View.GONE);
                                    isVisible = true;
                                }
                            }

                            @Override
                            public void OnCheckBoxListener(List<Genres> stringList) {
                                if (!with_genres.equals("")) {
                                    with_genres = "";
                                }
                                if (stringList.size() != 0) {
                                    for (int i = 0; i < stringList.size() - 1; i++) {
                                        with_genres = with_genres + stringList.get(i).getId() + " | ";
                                    }
                                    with_genres = with_genres + stringList.get(stringList.size() - 1).getId();
                                }
                                binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                                        intent.putExtra("media_type","movie");
                                        intent.putExtra("with_genres",with_genres);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                        binding.recycleViewGenres.setAdapter(collectionAdapter);
                    }

                    @Override
                    public void onFailure(Call<MovieInfo> call, Throwable t) {

                    }
                });
            }
        }
    }
}