package com.example.myapp.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.adapter.CollectionAdapter;
import com.example.myapp.adapter.CustomAdapter;
import com.example.myapp.databinding.FragmentSearchBinding;
import com.example.myapp.model.CustomModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private CustomAdapter customAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recycleview.setLayoutManager(layoutManager);

        List<CustomModel> customModelList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            customModelList.add(new CustomModel("Text "+ i ));
        }
        customAdapter = new CustomAdapter(customModelList);
        binding.recycleview.setAdapter(customAdapter);

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
                Toast.makeText(getContext(), "Submit: " + query, Toast.LENGTH_SHORT).show();
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getContext(),"Change: " + newText, Toast.LENGTH_SHORT).show();
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}