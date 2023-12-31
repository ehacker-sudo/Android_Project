package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.model.film.Image;

import java.util.List;

public class SecondAlbumAdapter extends RecyclerView.Adapter<SecondAlbumAdapter.ViewHolder> {
    private Context context;
    private List<Image> imageList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgFilm;

        public ViewHolder(View view) {
            super(view);
            imgFilm = view.findViewById(R.id.img_film);
        }
    }

    public SecondAlbumAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.second_item_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Image image = imageList.get(position);
        if (image == null) {
            return;
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + image.getFile_path()).into(viewHolder.imgFilm);
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
