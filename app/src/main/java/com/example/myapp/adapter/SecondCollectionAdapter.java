package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.film_interface.SecondCollectListener;

import java.util.List;

public class SecondCollectionAdapter extends RecyclerView.Adapter<SecondCollectionAdapter.ViewHolder>{
    private Context context;
    private List<Item> collection;
    private SecondCollectListener collectListener;
    private boolean isVisible;

    public SecondCollectionAdapter(Context context, List<Item> collection) {
        this.context = context;
        this.collection = collection;
    }

    public void setCollectListener(SecondCollectListener collectListener) {
        this.collectListener = collectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collect, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = collection.get(position);
        if (item == null) {
            return;
        }
        holder.tvTitle.setText(item.getNameGroup());
        holder.tvDesc.setVisibility(View.GONE);
        holder.arrowUp.setVisibility(View.GONE);
        isVisible = false;

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectListener.OnClickListener(holder);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recycleViewItem.setLayoutManager(linearLayoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(context,item.getGenresList());
        itemAdapter.setSecondCollectListener(collectListener);
        holder.recycleViewItem.setAdapter(itemAdapter);


    }

    @Override
    public int getItemCount() {
        if (collection != null) {
            return collection.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public LinearLayout tvDesc;
        public ImageView arrowUp;
        public ImageView arrowDown;
        public RecyclerView recycleViewItem;


        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDesc = view.findViewById(R.id.tv_desc);
            recycleViewItem = view.findViewById(R.id.recycle_view_item);
            arrowDown = view.findViewById(R.id.arrow_down);
            arrowUp = view.findViewById(R.id.arrow_up);
        }
    }

}
