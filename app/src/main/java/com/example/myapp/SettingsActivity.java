package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapp.adapter.QuantityAdapter;
import com.example.myapp.adapter.UserAdapter;
import com.example.myapp.databinding.ActivitySettingsBinding;
import com.example.myapp.film_interface.QuantityListener;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private UserAdapter userAdapter;
    private QuantityAdapter quantityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userAdapter = new UserAdapter();
        userAdapter.setStringList(getListUser());

        binding.list.setAdapter(userAdapter);
        binding.list.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if(binding.list.isHeaderCollapsed(headerId)){
                    binding.list.expand(headerId);
                }else {
                    binding.list.collapse(headerId);
                }
            }
        });
        setRecycleView();
    }

    private List<String> getListUser() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");

        list.add("B");
        list.add("B");
        list.add("B");
        list.add("B");
        list.add("B");

        list.add("C");
        list.add("C");
        list.add("C");
        list.add("C");
        list.add("C");
        list.add("C");

        list.add("D");
        list.add("D");
        list.add("D");
        list.add("D");
        list.add("D");
        list.add("D");
        list.add("D");

        list.add("E");
        list.add("E");
        list.add("E");
        list.add("E");
        list.add("E");
        list.add("E");


        return list;
    }


    private ArrayList<String> getData() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("10 kg");
        arrayList.add("11 kg");
        arrayList.add("12 kg");
        arrayList.add("13 kg");
        arrayList.add("14 kg");
        arrayList.add("15 kg");
        arrayList.add("16 kg");
        arrayList.add("17 kg");
        arrayList.add("18 kg");
        return arrayList;
    }

    private void setRecycleView() {
        binding.recycleView.setHasFixedSize(true);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        quantityAdapter = new QuantityAdapter(this, getData(), new QuantityListener() {
            @Override
            public void onQuatityChange(ArrayList<String> arrayList) {
                Toast.makeText(getApplicationContext(),arrayList.toString(),Toast.LENGTH_LONG).show();
            }
        });
        binding.recycleView.setAdapter(quantityAdapter);
    }

}