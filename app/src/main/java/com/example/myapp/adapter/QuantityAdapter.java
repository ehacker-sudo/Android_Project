package com.example.myapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.film_interface.QuantityListener;

import java.util.ArrayList;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;
    QuantityListener quantityListener;

    ArrayList<String> arrayList_0 = new ArrayList<>();

    public QuantityAdapter(Context context, ArrayList<String> arrayList, QuantityListener quantityListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.quantityListener = quantityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (arrayList != null && arrayList.size() > 0){
            holder.checkBox.setText(arrayList.get(position));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()){
                        arrayList_0.add(arrayList.get(position));
                    } else {
                        arrayList_0.remove(arrayList.get(position));
                    }

                    quantityListener.onQuatityChange(arrayList_0);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
