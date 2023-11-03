package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.myapp.adapter.ExpandedListViewadapter;
import com.example.myapp.databinding.ActivitySettingsBinding;
import com.example.myapp.film_interface.QuantityListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
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

        Map<GroupObject,List<ItemObject>> listItem = new HashMap<>();
        GroupObject groupObject2 = new GroupObject(1,"Kiểu phim");

        List<ItemObject> objectList2 = new ArrayList<>();
        objectList2.add(new ItemObject(1,"Phim ảnh"));
        objectList2.add(new ItemObject(2,"Phim truyền hình"));

        listItem.put(groupObject2,objectList2);

        List<GroupObject> listGroup = new ArrayList<>(listItem.keySet());

        ExpandableListAdapter expandableListAdapter = new ExpandedListViewadapter(listGroup, listItem, new QuantityListener() {
            @Override
            public void onQuatityChange(ArrayList<String> arrayList, String string) {

                if (arrayList.size() == 0 || arrayList.size() == 2) {
                    Map<GroupObject,List<ItemObject>> listItemGenres = new HashMap<>();

                    List<GroupObject> listGroupGenres = new ArrayList<>(listItemGenres.keySet());
                    ExpandableListAdapter Genres_expandableListAdapter = new ExpandedListViewadapter(listGroupGenres, listItemGenres, new QuantityListener() {
                        @Override
                        public void onQuatityChange(ArrayList<String> arrayList, String string) {
                            binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                    binding.expandableListView2.setAdapter(Genres_expandableListAdapter);
                    binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    Map<GroupObject,List<ItemObject>> listItemGenres = new HashMap<>();
                    GroupObject groupObject2 = new GroupObject(1,"Thể loại");

                    List<ItemObject> objectListGenres = new ArrayList<>();
                    objectListGenres.add(new ItemObject(1,"Hành động"));
                    objectListGenres.add(new ItemObject(2,"Phiêu lưu"));
                    objectListGenres.add(new ItemObject(3,"Hài"));

                    listItemGenres.put(groupObject2,objectListGenres);

                    List<GroupObject> listGroupGenres = new ArrayList<>(listItemGenres.keySet());
                    ExpandableListAdapter Genres_expandableListAdapter = new ExpandedListViewadapter(listGroupGenres, listItemGenres, new QuantityListener() {
                        @Override
                        public void onQuatityChange(ArrayList<String> arrayList, String string) {
                            binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });

                    binding.expandableListView2.setAdapter(Genres_expandableListAdapter);

                    binding.advancedSearchBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(SettingsActivity.this, AdvanceSearchActivity.class);
                            if (arrayList.get(0).equals("Phim truyền hình")) {
                                intent.putExtra("media_type","tv");
                            } else {
                                intent.putExtra("media_type","movie");
                            }
                            startActivity(intent);
                        }
                    });
                }

            }
        });

        binding.expandableListView.setAdapter(expandableListAdapter);

    }


}