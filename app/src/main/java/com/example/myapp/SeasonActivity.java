package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.adapter.EpisodeAdapter;
import com.example.myapp.adapter.GenresAdapter;
import com.example.myapp.adapter.TvExtraInfoAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.databinding.ActivitySeasonBinding;
import com.example.myapp.film_interface.EpisodeListener;
import com.example.myapp.model.film.Episode;
import com.example.myapp.model.film.ExtraInfo;
import com.example.myapp.model.film.Season;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonActivity extends AppCompatActivity {
    private ActivitySeasonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Retrofit.retrofit.getTvSeasonsDetails(getIntent().getIntExtra("id",37854),1,"").enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                Season season = response.body();
                LinearLayoutManager extra_info_layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                binding.recycleviewEp.setLayoutManager(extra_info_layoutManager);

                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                binding.recycleviewEp.addItemDecoration(dividerItemDecoration);


                EpisodeAdapter episodeAdapter = new EpisodeAdapter(getApplicationContext(),season.getEpisodes());
                episodeAdapter.setEpisodeListener(new EpisodeListener() {
                    @Override
                    public void OnClickListener(Episode episode) {
                        Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                        intent.putExtra("series_id",getIntent().getIntExtra("id",37854));
                        intent.putExtra("season_number",1);
                        intent.putExtra("id",episode.getId());
                        intent.putExtra("media_type","episode");
                        startActivity(intent);
                    }
                });

                binding.recycleviewEp.setAdapter(episodeAdapter);
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {

            }
        });

    }
}