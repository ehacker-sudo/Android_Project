package com.example.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.film_interface.GenresListener;
import com.example.myapp.model.film.Genres;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    private List<Genres> genresList;
    private GenresListener genresListener;

    public GenresAdapter(List<Genres> genresList) {
        this.genresList = genresList;
    }

    public void setGenresListener(GenresListener genresListener) {
        this.genresListener = genresListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button btnFirmGenres;

        public ViewHolder(View view) {
            super(view);
            btnFirmGenres = view.findViewById(R.id.btn_firm_genres);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.button_genres, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Genres genres = genresList.get(position);
        if (genres == null) {
            return;
        }
        viewHolder.btnFirmGenres.setText(genres.getName());
        viewHolder.btnFirmGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genresListener.OnClick(genres);
            }
        });
    }
    @Override
    public int getItemCount() {
        return genresList.size();
    }
}

