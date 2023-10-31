package com.example.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.film_interface.CollectListener;
import com.example.myapp.film_interface.ExtraInfoListener;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.ExtraInfo;
import com.example.myapp.model.film.TvSerie;

import java.util.List;

public class TvExtraInfoAdapter extends RecyclerView.Adapter<TvExtraInfoAdapter.ViewHolder> {
    private Context context;
    private List<ExtraInfo> extraInfoList;
    private ExtraInfoListener extraInfoListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFilmContent;
        public TextView tvFirmInfo;
        public LinearLayout detailInfo;

        public ViewHolder(View view) {
            super(view);
            tvFilmContent = view.findViewById(R.id.tv_film_content);
            tvFirmInfo = view.findViewById(R.id.tv_firm_info);
            detailInfo = view.findViewById(R.id.detail_info);
        }
    }

    public TvExtraInfoAdapter(Context context, List<ExtraInfo> extraInfoList) {
        this.context = context;
        this.extraInfoList = extraInfoList;
    }

    public void setExtraInfoListener(ExtraInfoListener extraInfoListener) {
        this.extraInfoListener = extraInfoListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_extra_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//        TvSerie tvSerie = tvSerieList.get(position);
        ExtraInfo extraInfo = extraInfoList.get(position);
        viewHolder.tvFilmContent.setText(extraInfo.getName());
        viewHolder.tvFirmInfo.setText(extraInfo.getDetail());
        viewHolder.detailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraInfoListener.OnClickListener(extraInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return extraInfoList.size();
    }
}

