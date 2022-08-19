package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.final_project.R;
import com.example.final_project.api.BookAPI;
import com.example.final_project.App;
import com.example.final_project.model.SearchResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultFragment extends Fragment {
    public static final String TAG = SearchResultFragment.class.getName();
    public static final int MAX_RESULTS = 40;
    TextView textView;
    BookAPI api;
    View view;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        Bundle bundleRecive = getArguments();
        if(bundleRecive!= null) {
            String query= (String) bundleRecive.get("query_for_search");
            if(query!=null) {

            }
        }
        return view;
    }
}