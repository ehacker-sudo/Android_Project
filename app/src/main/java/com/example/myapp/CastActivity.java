package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.PeopleInfoAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityCastBinding;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.model.film.People;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastActivity extends AppCompatActivity {

    private ActivityCastBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit.retrofit.getPeopleDetail(getIntent().getLongExtra("id",65510)).enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                People people = response.body();
                binding.contentCast.tvCastName.setText(people.getName());

                LinearLayoutManager layoutManager = new LinearLayoutManager(CastActivity.this, RecyclerView.HORIZONTAL, false);
                binding.contentCast.recycleViewCastInfo.setLayoutManager(layoutManager);
                List<String> peopleInfoList = new ArrayList<>();
                peopleInfoList.add(people.getAlso_known_as()[1]);
                peopleInfoList.add(people.getAlso_known_as()[2]);
                peopleInfoList.add(people.getKnown_for_department());
                PeopleInfoAdapter peopleInfoAdapter = new PeopleInfoAdapter(CastActivity.this,peopleInfoList);
                binding.contentCast.recycleViewCastInfo.setAdapter(peopleInfoAdapter);

                binding.contentCast.tvCastDesc.setText(people.getBiography());
                Glide.with(CastActivity.this).load("https://image.tmdb.org/t/p/w500" + people.getProfile_path()).into(binding.contentCast.imgCast);
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }
}