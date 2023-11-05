package com.example.myapp.api;

import com.example.myapp.model.film.Movie;
import com.example.myapp.model.resource.FilmResource;
import com.example.myapp.model.resource.VideoResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RapidApi {
    //Link Api : "https://ipinfo.io/161.185.160.93/geo"
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    RapidApi retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("https://youtube-video-details.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RapidApi.class);

    @Headers({
            "Accept: application/json",
            "X-RapidAPI-Key: ec72ec3a05msh7b266ea2fe81cd0p1f934bjsn854fc8d7be27",
            "X-RapidAPI-Host: youtube-video-details.p.rapidapi.com"
    })
    @GET("/")
    Call<VideoResource> getYoutubeDetail(
            @Query("id") String id
    );

}
