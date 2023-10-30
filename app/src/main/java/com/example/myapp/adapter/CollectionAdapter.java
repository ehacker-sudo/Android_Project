package com.example.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.api.ApiService;
import com.example.myapp.api.Retrofit;
import com.example.myapp.film_interface.CollectListener;
import com.example.myapp.filter.PaginateActivity;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>{
    private Context context;
    private String[] collection;
    private CollectListener collectListener;

    public CollectionAdapter(Context context, String[] collection) {
        this.context = context;
        this.collection = collection;
    }

    public void setCollectListener(CollectListener collectListener) {
        this.collectListener = collectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collection_home, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String collect = collection[position];
        if (collect == null) {
            return;
        }
        holder.tvCollectionName.setText(collect);
        holder.loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectListener.OnClickListener(collect);
            }
        });
        if (collect == "Các bộ phim") {
            Retrofit.retrofit.getPopularMovie("vi-Vn", 1).enqueue(ApiService.PopularMovieCallback(holder,context));
        } else if (collect == "Các chương trình truyền hình") {
            Retrofit.retrofit.getPopularTvSeries("vi-Vn", 1).enqueue(ApiService.PopularTvSeriesCallback(holder,context));
        } else if (collect == "Chương trình phồ biến gần đây") {
            Retrofit.retrofit.getTrendingTvSeries("day", "en").enqueue(ApiService.PopularTvSeriesCallback(holder,context));
        } else if (collect == "Bộ phim phổ biến gần đây") {
            Retrofit.retrofit.getTrendingMovies("day","en").enqueue(ApiService.PopularMovieCallback(holder,context));
        } else if (collect == "Bộ phim sắp ra mắt") {
            Retrofit.retrofit.getUpcomingMovies("en",1).enqueue(ApiService.PopularMovieCallback(holder,context));
        }

    }

    @Override
    public int getItemCount() {
        if (collection != null) {
            return collection.length;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCollectionName;
        public RecyclerView recycleviewMovie;
        public LinearLayout loadMore;


        public ViewHolder(View view) {
            super(view);
            tvCollectionName = view.findViewById(R.id.tv_collection_name);
            recycleviewMovie = view.findViewById(R.id.recycleview_movie);
            loadMore = view.findViewById(R.id.load_more);
        }
    }

}
