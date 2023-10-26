package com.example.myapp.api;

import com.example.myapp.model.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Retrofit {
    //Link Api : "https://ipinfo.io/161.185.160.93/geo"
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Retrofit.class);


    @GET("3/movie/popular")
    Call<Collection> getPopularMovie(
            @Header("Authorization") String authorization,
            @Query("language") String language,
            @Query("page") int page
    );
}
