package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.model.Movie;

import java.util.List;
import com.bumptech.glide.Glide;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieName;
        public ImageView movieImage;
        public TextView movieRate;

        public ViewHolder(View view) {
            super(view);
            movieName = view.findViewById(R.id.movie_name);
            movieImage = view.findViewById(R.id.movie_image);
            movieRate = view.findViewById(R.id.movie_rate);
        }
    }

    public HomeAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Movie movie = movieList.get(position);
        if (movie == null) {
            return;
        }
        viewHolder.movieName.setText(movie.getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" +movie.getPoster_path()).into(viewHolder.movieImage);
        viewHolder.movieRate.setText(Double.toString(movie.getVote_average()));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
