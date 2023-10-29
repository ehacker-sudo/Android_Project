package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.model.credit.Cast;
import com.example.myapp.model.credit.Crew;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ViewHolder> {
    private String[] departments;
    private List<Crew> crewList;

    public CrewAdapter(String[] departments, List<Crew> crewList) {
        this.departments = departments;
        this.crewList = crewList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFirmContent;
        public TextView tvFirmInfo;

        public ViewHolder(View view) {
            super(view);
            tvFirmContent = view.findViewById(R.id.tv_firm_content);
            tvFirmInfo = view.findViewById(R.id.tv_firm_info);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_extra_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String department = departments[position];
        if (department == null) {
            return;
        }

        viewHolder.tvFirmContent.setText(department);
        viewHolder.tvFirmInfo.setText("Xem tất cả");
    }


    @Override
    public int getItemCount() {
        return departments.length;
    }
}
