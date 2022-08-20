package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.final_project.R;
import com.example.final_project.api.BookAPI;
import com.example.final_project.App;
import com.example.final_project.Adapter.BookListAdapter;
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
    Button Backbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);

        App app= (App) mainActivity.getApplication();
        api = app.getApi();
        Bundle bundleReceive = getArguments();
        if(bundleReceive!= null) {
            String query= (String) bundleReceive.get("query_for_search");

            if(query!=null) {
                Call<SearchResult> call = api.searchBook(query, MAX_RESULTS);
                call.enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        SearchResult result = response.body();
                        if (result != null && result.getTotalItems() > 0) {
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {

                    }
                });
            }
        }
        Backbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(getParentFragmentManager()!=null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }
}