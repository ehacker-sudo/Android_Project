package com.example.myapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Key;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.Search;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Search> keyList;
    private FilmClickListener filmClickListener;

    public SearchAdapter(Context context, List<Search> keyList) {
        this.context = context;
        this.keyList = keyList;
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
        Search search = keyList.get(position);
        if (search == null) {
            return;
        }
        if (search.getMedia_type().equals("tv")) {
            viewHolder.tvName.setText(search.getName());
            viewHolder.tvInfo.setText(search.getFirst_air_date());
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + search.getPoster_path()).into(viewHolder.imgMovie);
            viewHolder.itemSearchedMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TvSerie tvSerie = new TvSerie();
                    tvSerie.setId(search.getId());
                    tvSerie.setMedia_type(search.getMedia_type());
                    filmClickListener.onClickItemTvSerie(tvSerie);
                }
            });
        } else if (search.getMedia_type().equals("movie")) {
            viewHolder.tvName.setText(search.getTitle());
            viewHolder.tvInfo.setText(search.getRelease_date());
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + search.getPoster_path()).into(viewHolder.imgMovie);
            viewHolder.itemSearchedMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = new Movie();
                    movie.setId(search.getId());
                    movie.setMedia_type(search.getMedia_type());
                    filmClickListener.onClickItemMovie(movie);
                }
            });
        } else if(search.getMedia_type().equals("person")) {
            viewHolder.tvName.setText(search.getName());
            viewHolder.tvInfo.setText("");
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + search.getProfile_path()).into(viewHolder.imgMovie);
        }
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }
}