package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapp.adapter.AlbumAdapter;
import com.example.myapp.adapter.MovieAdapter;
import com.example.myapp.adapter.PeopleInfoAdapter;
import com.example.myapp.adapter.TvSerieAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.ActivityCastBinding;
import com.example.myapp.databinding.ActivityMovieBinding;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.People;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.resource.ImageType;
import com.example.myapp.model.resource.MovieCreditResource;
import com.example.myapp.model.resource.TvCreditResource;

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
            //  Block of code to try
            Retrofit.retrofit.getPeopleDetail(getIntent().getIntExtra("id",65510),"en").enqueue(new Callback<People>() {
                @Override
                public void onResponse(Call<People> call, Response<People> response) {
                    People people = response.body();
                binding.contentCast.tvCastName.setText(people.getName());
                binding.toolbarTitle.setText(people.getName());
//                    Toast.makeText(CastActivity.this, people.getName(), Toast.LENGTH_LONG).show();

                LinearLayoutManager layoutManager = new LinearLayoutManager(CastActivity.this, RecyclerView.HORIZONTAL, false);
                binding.contentCast.recycleViewCastInfo.setLayoutManager(layoutManager);
                List<String> peopleInfoList = new ArrayList<>();
                if (!people.getAlso_known_as().isEmpty()) {
                    peopleInfoList.addAll(people.getAlso_known_as());
                }
                peopleInfoList.add(people.getKnown_for_department());
                PeopleInfoAdapter peopleInfoAdapter = new PeopleInfoAdapter(CastActivity.this,peopleInfoList);
                binding.contentCast.recycleViewCastInfo.setAdapter(peopleInfoAdapter);
//
                String desc = "";
                if (people.getBiography().length() > 200) {
                    for (int i = 0; i < 200; i++) {
                        desc = desc + people.getBiography().charAt(i);
                    }
                    binding.contentCast.nextImage.setVisibility(View.VISIBLE);
                    binding.contentCast.castMoreDesc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CastActivity.this, DescriptionActivity.class);
                            intent.putExtra("bio",people.getBiography());
                            intent.putExtra("name",people.getName());
                            startActivity(intent);
                        }
                    });
                }
                else {
                    desc = people.getBiography();
                    binding.contentCast.nextImage.setVisibility(View.GONE);
                    binding.contentCast.castMoreDesc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                binding.contentCast.tvCastDesc.setText(desc);
//
//
                Glide.with(CastActivity.this).load("https://image.tmdb.org/t/p/w500" + people.getProfile_path()).into(binding.contentCast.imgCast);
//
                Retrofit.retrofit.getPeopleImage(people.getId()).enqueue(new Callback<ImageType>() {
                    @Override
                    public void onResponse(Call<ImageType> call, Response<ImageType> response) {
                        ImageType imageType = response.body();

                        List<SlideModel> imageList = new ArrayList(); // Create image list

                        if (imageType.getProfiles().size() < 3) {
                            for (int i = 0; i < imageType.getProfiles().size(); i++) {
                                imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(i).getFile_path(), "", ScaleTypes.CENTER_CROP));
                            }
                        } else {
                          imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(0).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(1).getFile_path(), "",ScaleTypes.CENTER_CROP));
                            imageList.add(new SlideModel("https://image.tmdb.org/t/p/w500" + imageType.getProfiles().get(2).getFile_path(), "",ScaleTypes.CENTER_CROP));
                        }

                        ImageSlider imageSlider = binding.contentCast.imageSlider;
                        imageSlider.setImageList(imageList);


                        LinearLayoutManager layoutManager = new LinearLayoutManager(CastActivity.this, RecyclerView.HORIZONTAL,false);
                        binding.contentCast.recycleviewImageMovie.setLayoutManager(layoutManager);

                        AlbumAdapter albumAdapter = new AlbumAdapter(CastActivity.this,imageType.getProfiles());
                        binding.contentCast.recycleviewImageMovie.setAdapter(albumAdapter);

                        binding.contentCast.textViewCastBirthday.setText(people.getBirthday());

                        binding.contentCast.textViewCastPlaceOfBirth.setText(people.getPlace_of_birth());


                    }

                    @Override
                    public void onFailure(Call<ImageType> call, Throwable t) {

                    }
                });

                Retrofit.retrofit.getPeopleTvSerieCredits(people.getId(),"en").enqueue(new Callback<TvCreditResource>() {
                    @Override
                    public void onResponse(Call<TvCreditResource> call, Response<TvCreditResource> response) {
                        TvCreditResource tvCreditResource = response.body();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(CastActivity.this,RecyclerView.HORIZONTAL,false);
                        binding.contentCast.recycleviewCastTvSerie.setLayoutManager(layoutManager);

                        if (tvCreditResource != null) {
                            TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(CastActivity.this,tvCreditResource.getCast());
                            tvSerieAdapter.setFilmClickListener(new FilmClickListener() {
                                @Override
                                public void onClickItemMovie(Movie movie) {

                                }

                                @Override
                                public void onClickItemTvSerie(TvSerie tvSerie) {
                                    Intent intent = new Intent(CastActivity.this, MovieActivity.class);
                                    intent.putExtra("id",tvSerie.getId());
                                    intent.putExtra("media_type","tv");
                                    startActivity(intent);
                                }
                            });
                            binding.contentCast.recycleviewCastTvSerie.setAdapter(tvSerieAdapter);
                        }
                        else {
                            TvSerieAdapter tvSerieAdapter = new TvSerieAdapter(CastActivity.this,new ArrayList<>());
                            binding.contentCast.recycleviewCastTvSerie.setAdapter(tvSerieAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<TvCreditResource> call, Throwable t) {

                    }
                });

                Retrofit.retrofit.getPeopleMovieCredits(people.getId(),"en").enqueue(new Callback<MovieCreditResource>() {
                    @Override
                    public void onResponse(Call<MovieCreditResource> call, Response<MovieCreditResource> response) {
                        MovieCreditResource movieFilmResource = response.body();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(CastActivity.this, RecyclerView.HORIZONTAL, false);
                        binding.contentCast.recycleviewCastMovie.setLayoutManager(layoutManager);

                        if (movieFilmResource != null) {
                            MovieAdapter movieAdapter = new MovieAdapter(CastActivity.this, movieFilmResource.getCast());
                            movieAdapter.setFilmClickListener(new FilmClickListener() {
                                @Override
                                public void onClickItemMovie(Movie movie) {
                                    Intent intent = new Intent(CastActivity.this, MovieActivity.class);
                                    intent.putExtra("id",movie.getId());
                                    intent.putExtra("media_type","movie");
                                    startActivity(intent);
                                }

                                @Override
                                public void onClickItemTvSerie(TvSerie tvSerie) {

                                }
                            });
                            binding.contentCast.recycleviewCastMovie.setAdapter(movieAdapter);
                        } else {
                            MovieAdapter movieAdapter = new MovieAdapter(CastActivity.this,new ArrayList<>());
                            binding.contentCast.recycleviewCastMovie.setAdapter(movieAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieCreditResource> call, Throwable t) {

                    }
                });


                }

                @Override
                public void onFailure(Call<People> call, Throwable t) {

                }
            });
    }
}