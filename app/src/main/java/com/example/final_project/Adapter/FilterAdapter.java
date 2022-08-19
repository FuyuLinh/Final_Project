package com.example.final_project.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.final_project.R;
import com.example.final_project.model.Filter;

import java.util.List;

public class FilterAdapter extends ArrayAdapter<Filter> {

    public FilterAdapter(Context context, int resource, List<Filter> objects) {
        super(context, resource, objects);

    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_filter,parent,false);
        TextView selectedText = convertView.findViewById(R.id.selected);

        Filter filter = this.getItem(position);
        if(filter!=null)
        {
            selectedText.setText(filter.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_filter,parent,false);
        TextView filterText = convertView.findViewById(R.id.filter);

        Filter filter = this.getItem(position);
        if(filter!=null)
        {
            filterText.setText(filter.getName());
        }
        return convertView;
    }
}
