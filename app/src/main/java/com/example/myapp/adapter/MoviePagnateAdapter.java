package com.example.myapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;

import java.util.List;

public class MoviePagnateAdapter extends RecyclerView.Adapter<MoviePagnateAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;
    private FilmClickListener filmClickListener;

    public MoviePagnateAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public void setFilmClickListener(FilmClickListener filmClickListener) {
        this.filmClickListener = filmClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout itemSearchedMovie;
        public ImageView imgMovie;
        public TextView tvInfo;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            itemSearchedMovie = view.findViewById(R.id.item_searched_movie);
            imgMovie = view.findViewById(R.id.img_movie);
            tvInfo = view.findViewById(R.id.tv_info);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_search, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        Movie movie = movieList.get(position);
        if (movie == null) {
            return;
        }
        viewHolder.tvName.setText(movie.getTitle());
        viewHolder.tvInfo.setText(movie.getRelease_date());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(viewHolder.imgMovie);
        viewHolder.itemSearchedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmClickListener.onClickItemMovie(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}