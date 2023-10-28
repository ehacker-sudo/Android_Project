package com.example.myapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapp.R;
import com.example.myapp.adapter.CollectionAdapter;
import com.example.myapp.auth.AuthActivity;
import com.example.myapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rcvCollection.setLayoutManager(layoutManager);

        String[] data = {
                "Các bộ phim",
                "Các chương trình truyền hình",
                "Chương trình phồ biến gần đây",
                "Bộ phim phổ biến gần đây",
                "Bộ phim sắp ra mắt"
        };
        CollectionAdapter collectionAdapter = new CollectionAdapter(getContext(),data);
        binding.rcvCollection.setAdapter(collectionAdapter);

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
        inflater.inflate(R.menu.home_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user) {
            Intent intent = new Intent(getContext().getApplicationContext(), AuthActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}