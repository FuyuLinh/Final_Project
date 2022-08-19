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
import android.widget.Spinner;

import com.example.final_project.Adapter.FilterAdapter;
import com.example.final_project.R;
import com.example.final_project.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private Button searchquery;
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
        searchquery = view.findViewById(R.id.searchquery);
        query = view.findViewById(R.id.query);
        appIcon = view.findViewById(R.id.appIcon);

        spnFilter = view.findViewById(R.id.Spiner);
        filterAdapter = new FilterAdapter(mainActivity, R.layout.selected_filter, getListFilter());
        spnFilter.setAdapter(filterAdapter);
        final String[] filter_search = new String[1];
        spnFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter_search[0] = new String(filterAdapter.getItem(position).getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query_for_search = query.getText().toString();
                String str = filter_search[0];
                mainActivity.gotoBlank(query_for_search,str);
            }
        });
        return view;
    }

    private List<Filter> getListFilter() {
        List<Filter> list = new ArrayList<>();
        list.add(new Filter("Author"));
        list.add(new Filter("Tilte"));
        list.add(new Filter("Published Date"));
        return list;
    }
}