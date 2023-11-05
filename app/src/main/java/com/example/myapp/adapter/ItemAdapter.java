package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.film_interface.SecondCollectListener;
import com.example.myapp.model.film.Genres;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private Context context;
    private List<Genres> genresList;
    private SecondCollectListener secondCollectListener;
    private List<Genres> arrayList1 = new ArrayList<>();


    public ItemAdapter(Context context, List<Genres> genresList) {
        this.context = context;
        this.genresList = genresList;
    }

    public void setSecondCollectListener(SecondCollectListener secondCollectListener) {
        this.secondCollectListener = secondCollectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genres genres = genresList.get(position);
        if (genres == null) {
            return;
        }
        holder.tvTitle.setText(genres.getName());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvTitle.isChecked()){
                    arrayList1.add(genres);
                } else {
                    arrayList1.remove(genres);
                }
                secondCollectListener.OnCheckBoxListener(arrayList1);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (genresList != null) {
            return genresList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox tvTitle;


        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_item);
        }
    }

}
