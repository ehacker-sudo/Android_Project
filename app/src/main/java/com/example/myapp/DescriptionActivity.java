package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myapp.databinding.ActivityDescriptionBinding;
import com.example.myapp.databinding.ActivityMovieBinding;

public class DescriptionActivity extends AppCompatActivity {
    private ActivityDescriptionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(getIntent().getStringExtra("name"));

        binding.castBio.textViewBioCast.setText(getIntent().getStringExtra("bio"));

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}