package com.example.myapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.model.film.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public ImageView movieImage;
        public TextView movieRate;
        public CardView card_movie;

        public ViewHolder(View view) {
            super(view);
            movieName = view.findViewById(R.id.movie_name);
            movieImage = view.findViewById(R.id.movie_image);
            movieRate = view.findViewById(R.id.movie_rate);
            card_movie = view.findViewById(R.id.card_movie);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Movie movie = movieList.get(position);
        if (movie == null) {
            return;
        }
        viewHolder.movieName.setText(movie.getTitle());
        viewHolder.movieRate.setText(Double.toString(movie.getVote_average()));
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(viewHolder.movieImage);
        viewHolder.card_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToDetail(movie);
            }
        });
    }

    private void MoveToDetail(Movie movie) {
        Intent intent = new Intent(context, MovieActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}