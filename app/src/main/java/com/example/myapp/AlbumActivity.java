package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapp.adapter.AlbumAdapter;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityAlbumBinding;
import com.example.myapp.databinding.ActivityVideoBinding;
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

        Retrofit.retrofit.getTVSeriesImages(getIntent().getIntExtra("id",37854),"")
                .enqueue(new Callback<ImageType>() {
                    @Override
                    public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                        ImageType imageType = response.body();


                        GridLayoutManager gridLayoutManager = new GridLayoutManager(AlbumActivity.this, 2);
                        binding.recycleViewAlbum.setLayoutManager(gridLayoutManager);

                        AlbumAdapter albumAdapter = new AlbumAdapter(AlbumActivity.this,imageType.getBackdrops());
                        binding.recycleViewAlbum.setAdapter(albumAdapter);
                    }

                    @Override
                    public void onFailure(Call<ImageType> call, Throwable t) {

                    }
                });
    }
}