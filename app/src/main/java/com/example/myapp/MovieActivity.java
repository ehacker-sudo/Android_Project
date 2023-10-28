package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityMovieBinding;
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

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Retrofit.retrofit.getTVSeriesImages(37854,"").enqueue(new Callback<ImageType>() {
            @Override
            public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                ImageType imageType = response.body();

                List<SlideModel> imageList = new ArrayList(); // Create image list


                if (imageType.getBackdrops().size() < 3) {
                    imageList.add(new SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years.",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct.",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://bit.ly/3fLJf72", "And people do that.",ScaleTypes.CENTER_CROP));
                }
                else {
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                    imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getBackdrops().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                }

                ImageSlider imageSlider = findViewById(R.id.image_slider);
                imageSlider.setImageList(imageList);
            }

            @Override
            public void onFailure(Call<ImageType> call, Throwable t) {

            }
        });

        binding.navView.setOnItemReselectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.navigation_search) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.navigation_video) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}