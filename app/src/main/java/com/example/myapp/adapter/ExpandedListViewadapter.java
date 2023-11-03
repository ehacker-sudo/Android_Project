package com.example.myapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myapp.GroupObject;
import com.example.myapp.ItemObject;
import com.example.myapp.R;
import com.example.myapp.film_interface.QuantityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExpandedListViewadapter extends BaseExpandableListAdapter {
    private List<GroupObject> listGroup;
    private Map<GroupObject,List<ItemObject>> listItem;
    private QuantityListener quantityListener;
    private ArrayList<String> arrayList_0 = new ArrayList<>();

    public ExpandedListViewadapter(List<GroupObject> listGroup, Map<GroupObject, List<ItemObject>> listItem, QuantityListener quantityListener) {
        this.listGroup = listGroup;
        this.listItem = listItem;
        this.quantityListener = quantityListener;
    }

    @Override
    public int getGroupCount() {
        if (listGroup != null) {
            return listGroup.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listGroup != null && listItem != null) {
            return listItem.get(listGroup.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        GroupObject groupObject = listGroup.get(groupPosition);
        return groupObject.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        ItemObject itemObject = listItem.get(listGroup.get(groupPosition)).get(childPosition);
        return itemObject.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group,parent,false);
        }
        TextView tvGroup = convertView.findViewById(R.id.tv_group);
        GroupObject groupObject = listGroup.get(groupPosition);
        tvGroup.setText(groupObject.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        }
        CheckBox tvItem = convertView.findViewById(R.id.tv_item);
        ItemObject itemObject = listItem.get(listGroup.get(groupPosition)).get(childPosition);
        tvItem.setText(itemObject.getName());
        GroupObject groupObject = listGroup.get(groupPosition);
        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvItem.isChecked()){
                    arrayList_0.add(itemObject.getName());
                } else {
                    arrayList_0.remove(itemObject.getName());
                }

                quantityListener.onQuatityChange(arrayList_0,listGroup.get(groupPosition).getName());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
