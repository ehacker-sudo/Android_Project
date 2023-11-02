package com.example.myapp.ui.search;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.myapp.MovieActivity;
import com.example.myapp.R;
import com.example.myapp.SettingsActivity;
import com.example.myapp.adapter.SearchAdapter;
import com.example.myapp.api.Retrofit;
import com.example.myapp.databinding.FragmentSearchBinding;
import com.example.myapp.film_interface.FilmClickListener;
import com.example.myapp.model.film.Key;
import com.example.myapp.model.film.Movie;
import com.example.myapp.model.film.Search;
import com.example.myapp.model.film.TvSerie;
import com.example.myapp.model.resource.FilmResource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        binding.anvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
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
                Retrofit.retrofit.getMultiSearch(query,"",1).enqueue(new Callback<FilmResource<Search>>() {
                    @Override
                    public void onResponse(Call<FilmResource<Search>> call, Response<FilmResource<Search>> response) {
                        FilmResource<Search> searchFilmResource = response.body();

                        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                        binding.recycleview.addItemDecoration(dividerItemDecoration);

                        SearchAdapter searchAdapter = new SearchAdapter(getContext(),searchFilmResource.getResults());
                        searchAdapter.setFilmClickListener(new FilmClickListener() {
                            @Override
                            public void onClickItemMovie(Movie movie) {
                                Intent intent = new Intent(getContext(), MovieActivity.class);
                                intent.putExtra("id",movie.getId());
                                intent.putExtra("media_type","movie");
                                startActivity(intent);
                            }

                            @Override
                            public void onClickItemTvSerie(TvSerie tvSerie) {
                                Intent intent = new Intent(getContext(), MovieActivity.class);
                                intent.putExtra("id",tvSerie.getId());
                                intent.putExtra("media_type","tv");
                                startActivity(intent);
                            }
                        });
                        binding.recycleview.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onFailure(Call<FilmResource<Search>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    SearchAdapter searchAdapter = new SearchAdapter(getContext(),new ArrayList<>());
                    binding.recycleview.setAdapter(searchAdapter);
                }
                return false;
            }
        });
    }
}