package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.final_project.R;
public class SearchFragment extends Fragment {
    Button searchquery;
    EditText query;
    ImageView appIcon;
    private View view;
    MainActivity mainActivity;

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
        searchquery = view.findViewById(R.id.searchquery);
        query = view.findViewById(R.id.query);
        appIcon = view.findViewById(R.id.appIcon);
        mainActivity = (MainActivity) getActivity();
        searchquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query_for_search = query.getText().toString();
                mainActivity.searchResult(query_for_search);
            }
        });
        return view;
    }
}