package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.film_interface.CastListener;
import com.example.myapp.model.credit.Cast;

import java.util.List;
import com.bumptech.glide.Glide;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private Context context;
    private List<Cast> castList;
    private CastListener castListener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCastName;
        public ImageView castImage;
        public TextView tvCastRole;
        public LinearLayout cardMovie;

        public ViewHolder(View view) {
            super(view);
            tvCastName = view.findViewById(R.id.tv_cast_name);
            castImage = view.findViewById(R.id.cast_image);
            tvCastRole = view.findViewById(R.id.tv_cast_role);
            cardMovie = view.findViewById(R.id.card_movie);
        }
    }

    public CastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    public void setCastListener(CastListener castListener) {
        this.castListener = castListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cast, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Cast cast = castList.get(position);
        if (cast == null) {
            return;
        }
        viewHolder.tvCastName.setText(cast.getName());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + cast.getProfile_path()).into(viewHolder.castImage);
        viewHolder.tvCastRole.setText(cast.getCharacter());
        viewHolder.cardMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                castListener.onClick(cast);
            }
        });
    }

//    private void MoveToDetail(Movie movie) {
//        Intent intent = new Intent(context, MovieActivity.class);
//        context.startActivity(intent);
//    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
