package com.example.final_project.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.final_project.R;
import com.example.final_project.model.Book;

public class MainActivity extends AppCompatActivity {
    FragmentContainerView fragmentContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentContainerView=findViewById((R.id.fragmentContainerView));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        fragmentTransaction.replace(R.id.fragmentContainerView,searchFragment);
        fragmentTransaction.commit();
    }

    public void gotoSearchResults(String q){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SearchResultFragment resultFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("query",q);
        resultFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView,resultFragment);
        fragmentTransaction.addToBackStack(SearchResultFragment.TAG);
        fragmentTransaction.commit();
    }

    public void gotoDetailFragment(Book book) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object",book);
        detailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView,detailFragment);
        fragmentTransaction.addToBackStack(DetailFragment.TAG);
        fragmentTransaction.commit();
    }
}