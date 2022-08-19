package com.example.final_project.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.final_project.R;

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
    public void gotoBlank(String querry, String filter){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("query_for_search",querry +'/'+ filter);
        blankFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentContainerView,blankFragment);
        fragmentTransaction.addToBackStack(BlankFragment.TAG);
        fragmentTransaction.commit();
    }
}