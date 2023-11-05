package com.example.myapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapp.R;
import com.example.myapp.api.RapidApi;
import com.example.myapp.model.film.Image;
import com.example.myapp.model.film.Video;
import com.example.myapp.model.resource.VideoResource;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private Context context;
    private List<Video> videoList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout itemVideo;
        public TextView videoType;
        public TextView videoRuntime;
        public TextView videoName;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            itemVideo = view.findViewById(R.id.item_video);
            videoType = view.findViewById(R.id.video_type);
            videoRuntime = view.findViewById(R.id.video_runtime);
            videoName = view.findViewById(R.id.video_name);
            imageView = view.findViewById(R.id.video_image_view);
        }
    }

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Video video = videoList.get(position);
        if (video == null) {
            return;
        }

        viewHolder.videoType.setText(video.getType());
        new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          RapidApi.retrofit.getYoutubeDetail(video.getKey()).enqueue(new Callback<VideoResource>() {
                                                      @Override
                                                      public void onResponse(Call<VideoResource> call, Response<VideoResource> response) {
                                                          VideoResource videoResource = response.body();
                                                          Glide.with(context).load(videoResource.getThumbnails().get(videoResource.getThumbnails().size() - 1).getUrl()).into(viewHolder.imageView);

                                                          viewHolder.videoName.setText(videoResource.getTitle());
                                                          String runtime = "" + (int) Math.floor(videoResource.getLengthSeconds() / 60) + "." + (int) Math.floor(videoResource.getLengthSeconds() % 60);
                                                          viewHolder.videoRuntime.setText(runtime);

                                                      }

                                                      @Override
                                                      public void onFailure(Call<VideoResource> call, Throwable t) {

                                                      }
                                                  });
                      }

              },2000);
    }
}
