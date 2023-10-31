package com.example.myapp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class TvPagnateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;

    private boolean isLoadingAdd;
    private Context context;
    private List<TvSerie> tvSerieList;
    private FilmClickListener filmClickListener;

    public TvPagnateAdapter(Context context, List<TvSerie> tvSerieList) {
        this.context = context;
        this.tvSerieList = tvSerieList;
    }

    public void setTvSerieList(List<TvSerie> tvSerieList) {
        this.tvSerieList = tvSerieList;
    }

    public void setFilmClickListener(FilmClickListener filmClickListener) {
        this.filmClickListener = filmClickListener;
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public LinearLayout itemSearchedMovie;
        public ImageView imgMovie;
        public TextView tvInfo;

        public TvViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            itemSearchedMovie = view.findViewById(R.id.item_searched_movie);
            imgMovie = view.findViewById(R.id.img_movie);
            tvInfo = view.findViewById(R.id.tv_info);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        private ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progess_bar);
        }
    }

    public void addFooterLoading(){
        isLoadingAdd = true;
        tvSerieList.add(new TvSerie());
    }

    public void removeFooterLoading(){
        isLoadingAdd = false;

        int position = tvSerieList.size() - 1;
        TvSerie tvSerie = tvSerieList.get(position);
        if (tvSerie != null){
            tvSerieList.remove(tvSerie);
            notifyDataSetChanged();

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (tvSerieList != null && position == tvSerieList.size() - 1 && isLoadingAdd){
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (TYPE_ITEM == viewType){

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_search, viewGroup, false);
            return new TvViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_loading, viewGroup, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        if (viewHolder.getItemViewType() == TYPE_ITEM){
            TvSerie tvSerie = tvSerieList.get(position);
            TvViewHolder tvViewHolder = (TvViewHolder) viewHolder;
            tvViewHolder.tvName.setText(tvSerie.getName() + " "+ (position + 1));
            tvViewHolder.tvName.setText(tvSerie.getName());
            tvViewHolder.tvInfo.setText(tvSerie.getFirst_air_date());
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + tvSerie.getPoster_path()).into(tvViewHolder.imgMovie);
            tvViewHolder.itemSearchedMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filmClickListener.onClickItemTvSerie(tvSerie);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tvSerieList  .size();
    }
}