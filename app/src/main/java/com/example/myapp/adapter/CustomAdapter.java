package com.example.myapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.model.CustomModel;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {
    private List<CustomModel> customModelList;
    private List<CustomModel> customModelListFiltered;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    customModelList = customModelListFiltered;
                } else {
                    List<CustomModel> filteredList = new ArrayList<>();
                    for (CustomModel row : customModelListFiltered) {

                        if (row.getTxt().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    customModelList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                customModelList = (List<CustomModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    public CustomAdapter(List<CustomModel> customModelList) {
        this.customModelList = customModelList;
        this.customModelListFiltered = customModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.textView.setText(customModelList.get(position).getTxt());
    }

    @Override
    public int getItemCount() {
        return customModelList.size();
    }
}