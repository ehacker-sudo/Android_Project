package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.api.Retrofit;
import com.example.myapp.model.Collection;
import com.example.myapp.model.Movie;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder>{
    private Context context;
    private String[] collection;

    public CollectionAdapter(Context context, String[] collection) {
        this.context = context;
        this.collection = collection;
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
        Retrofit.retrofit.getPopularMovie(
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YmIwZjIwOTE1N2YwYmI0Nzg4ZWNiNTRiZTYzNWQxNCIsInN1YiI6IjY0MmE0OTkzMGYzNjU1MDBmMWMyOWZiNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.inC5WmHQXvHthA0THRYobk29Tea9Q5lpVyY2rfKCBd8",
                "vi-Vn",
                1
        ).enqueue(new retrofit2.Callback<Collection>() {
            @Override
            public void onResponse(retrofit2.Call<Collection> call, retrofit2.Response<Collection> response) {
                Collection collection = response.body();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
                holder.recycleviewMovie.setLayoutManager(layoutManager);

                if (collection != null) {
                    HomeAdapter homeAdapter = new HomeAdapter(context,collection.getResults());
                    holder.recycleviewMovie.setAdapter(homeAdapter);
                }
                else {
                    HomeAdapter homeAdapter = new HomeAdapter(context,new ArrayList<>());
                    holder.recycleviewMovie.setAdapter(homeAdapter);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<Collection> call, Throwable t) {

            }
        });

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

        public ViewHolder(View view) {
            super(view);
            tvCollectionName = view.findViewById(R.id.tv_collection_name);
            recycleviewMovie = view.findViewById(R.id.recycleview_movie);
        }
    }

}
