package com.example.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class TvExtraInfoAdapter extends RecyclerView.Adapter<TvExtraInfoAdapter.ViewHolder> {
    private Context context;
    private List<TvSerie> tvSerieList;
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

    public TvExtraInfoAdapter(Context context, List<TvSerie> tvSerieList) {
        this.context = context;
        this.tvSerieList = tvSerieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TvSerie tvSerie = tvSerieList.get(position);
        if (tvSerie == null) {
            return;
        }
        viewHolder.movieName.setText(tvSerie.getName());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + tvSerie.getPoster_path()).into(viewHolder.movieImage);
        viewHolder.movieRate.setText(Double.toString(tvSerie.getVote_average()));
        viewHolder.card_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToDetail(tvSerie);
            }
        });
    }

    private void MoveToDetail(TvSerie tvSerie) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("id",tvSerie.getId());
        intent.putExtra("media_type","tv");
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return tvSerieList.size();
    }
}

