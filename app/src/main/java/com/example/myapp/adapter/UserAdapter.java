package com.example.myapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class UserAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        private List<String> stringList;

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();

    }

    @Override
        public int getCount() {
            if (stringList != null) {
                return stringList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemViewHolder holder;

            if (convertView == null) {
                holder = new ItemViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
                holder.textItem = (TextView) convertView.findViewById(R.id.tv_item);
                convertView.setTag(holder);
            } else {
                holder = (ItemViewHolder) convertView.getTag();
            }

            holder.textItem.setText(stringList.get(position));

            return convertView;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            if (convertView == null) {
                holder = new HeaderViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header, parent, false);
                holder.textHeader = (TextView) convertView.findViewById(R.id.tv_header);
                convertView.setTag(holder);
            } else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            //set header text as first char in name
            holder.textHeader.setText(stringList.get(position).substring(0,1));
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //return the first character of the country as ID because this is what headers are based upon
            return stringList.get(position).subSequence(0, 1).charAt(0);
        }

        class HeaderViewHolder {
            private TextView textHeader;
        }

        class ItemViewHolder {
            private TextView textItem;
        }

    }