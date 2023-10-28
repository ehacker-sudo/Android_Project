package com.example.myapp.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;
import com.example.myapp.adapter.SearchAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.FragmentSearchBinding;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.resource.FilmResource;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recycleview.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);

        SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Retrofit.retrofit.getMovieSearch(query,"vi-Vn", 1).enqueue(new retrofit2.Callback<FilmResource<Movie>>() {
                    @Override
                    public void onResponse(retrofit2.Call<FilmResource<Movie>> call, retrofit2.Response<FilmResource<Movie>> response) {
                        FilmResource<Movie> filmResource = response.body();
                        List<Movie> movieList = new ArrayList<>();

                        for (int i = 0; i < filmResource.getResults().size(); i++) {
                            movieList.add(filmResource.getResults().get(i));
                        }

                        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                        binding.recycleview.addItemDecoration(dividerItemDecoration);

                        SearchAdapter searchAdapter = new SearchAdapter(getContext(),movieList);
                        binding.recycleview.setAdapter(searchAdapter);
                    }
                    @Override
                    public void onFailure(retrofit2.Call<FilmResource<Movie>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}