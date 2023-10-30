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
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class TvPagnateAdapter extends RecyclerView.Adapter<TvPagnateAdapter.ViewHolder> {
    private Context context;
    private List<TvSerie> tvSerieList;
    private FilmClickListener filmClickListener;

    public TvPagnateAdapter(Context context, List<TvSerie> tvSerieList) {
        this.context = context;
        this.tvSerieList = tvSerieList;
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
        TvSerie tvSerie = tvSerieList.get(position);
        if (tvSerie == null) {
            return;
        }
        viewHolder.tvName.setText(tvSerie.getName());
        viewHolder.tvInfo.setText(tvSerie.getFirst_air_date());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + tvSerie.getPoster_path()).into(viewHolder.imgMovie);
        viewHolder.itemSearchedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmClickListener.onClickItemTvSerie(tvSerie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvSerieList  .size();
    }
}