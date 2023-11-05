package com.example.myapp.film_interface;

import com.example.myapp.adapter.SecondCollectionAdapter;
import com.example.myapp.model.film.Genres;

import java.util.List;

public interface SecondCollectListener {
    public void OnClickListener(SecondCollectionAdapter.ViewHolder viewHolder);
    public void OnCheckBoxListener(List<Genres> stringList);
}
