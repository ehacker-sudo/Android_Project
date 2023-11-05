package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapp.api.RapidApi;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.databinding.ActivityVideoBinding;
import com.example.myapp.film_interface.VideoListener;
import com.example.myapp.model.resource.VideoResource;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements Callback<VideoResource> {

    private ActivityVideoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getLifecycle().addObserver(binding.ytbPlayer);
        binding.ytbPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getIntent().getStringExtra("id");
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

//        RapidApi.retrofit.getYoutubeDetail(getIntent().getStringExtra("id")).enqueue(new Callback<VideoResource>() {
//            @Override
//            public void onResponse(Call<VideoResource> call, Response<VideoResource> response) {
//                VideoResource videoResource = response.body();
//                getLifecycle().addObserver(binding.ytbPlayer);
//                binding.ytbPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                        String videoId = "mkJbEP5GeRA";
//                        youTubePlayer.loadVideo(videoId, 0);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<VideoResource> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void onResponse(Call<VideoResource> call, Response<VideoResource> response) {

    }

    @Override
    public void onFailure(Call<VideoResource> call, Throwable t) {

    }
}