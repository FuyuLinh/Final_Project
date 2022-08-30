package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.final_project.Adapter.FilterAdapter;
import com.example.final_project.R;
import com.example.final_project.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RelativeLayout searchquery;
    private EditText query;
    private ImageView appIcon;
    private Spinner spnFilter;
    private FilterAdapter filterAdapter;
    private View view;
    private MainActivity mainActivity;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        mainActivity = (MainActivity) getActivity();
        searchquery = view.findViewById(R.id.SearchButton);
        query = view.findViewById(R.id.query);
        appIcon = view.findViewById(R.id.appIcon);

        spnFilter = view.findViewById(R.id.Spiner);
        filterAdapter = new FilterAdapter(mainActivity, R.layout.selected_filter, getListFilter());
        spnFilter.setAdapter(filterAdapter);
        final int[] filter = new int[1];
        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = getQuery(query.getText().toString(),filter[0]);
                mainActivity.gotoSearchResults(q);
            }
        });
        return view;
    }

    private List<Filter> getListFilter() {
        List<Filter> list = new ArrayList<>();
        list.add(new Filter("Title"));
        list.add(new Filter("Author"));
        return list;
    }
    private String getQuery(String text, int type)
    {
        text= text.replaceAll("\\s+", " ");
        text = text.replace(' ','+');
        if(type==0)
            text = "intitle:" + text;
        else if(type==1)
            text = "+inauthor:" + text;


        return text;
    }
}