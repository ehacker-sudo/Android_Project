package com.example.myapp.model.resource;

import com.example.myapp.model.film.Image;

import java.util.List;

public class VideoResource {
    private List<Image> thumbnails;
    private String title;
    private String videoId;
    private long lengthSeconds;

    public List<Image> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<Image> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public long getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(long lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }
}
