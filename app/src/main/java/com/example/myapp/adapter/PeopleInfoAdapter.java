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
import com.example.myapp.model.film.People;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PeopleInfoAdapter extends RecyclerView.Adapter<PeopleInfoAdapter.ViewHolder>{
    private Context context;
    private List<String> peopleInfoList;


    public PeopleInfoAdapter(Context context, List<String> peopleInfoList) {
        this.context = context;
        this.peopleInfoList = peopleInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cast_info, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String people_info = peopleInfoList.get(position);
        if (people_info == null) {
            return;
        }
        holder.tvCastInfo.setText(people_info);

    }

    @Override
    public int getItemCount() {
        return peopleInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCastInfo;


        public ViewHolder(View view) {
            super(view);
            tvCastInfo = view.findViewById(R.id.text_view_cast_info);
        }
    }

}
