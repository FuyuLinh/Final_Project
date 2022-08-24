package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.final_project.R;
import com.example.final_project.Adapter.BookListAdapter;
import com.example.final_project.model.Book;


import java.util.ArrayList;


public class SearchResultFragment extends Fragment {
    public static final String TAG = SearchResultFragment.class.getName();
    TextView textView;
    View view;
    MainActivity mainActivity;
    Button backButton;

    private RequestQueue mRequestQueue;
    private ArrayList<Book> bookInfoArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        textView = view.findViewById(R.id.textview);
        backButton = view.findViewById(R.id.button2);
        mainActivity = (MainActivity)getActivity();
        Bundle bundleReceive = getArguments();
        if(bundleReceive!= null) {
            String str= (String) bundleReceive.get("query_for_search");
            String query= str.substring(0,str.indexOf('/'));
            if(query!=null) {
                textView.setText(query);
                getBooksInfo(query);
            }
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragmentManager()!=null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    private void getBooksInfo(String query) {
        // creating a new array list.
        bookInfoArrayList = new ArrayList<>();
        // below line is use to initialize
        // the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(mainActivity);

        // below line is use to clear cache this
        // will be use when our data is being updated.
        mRequestQueue.getCache().clear();
        // below is the url for getting data from API in json format.
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=40&key=AIzaSyAIB6Wjw3bm_yiGn1kuFdqbbV7LPa1hzNs";

        // below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside on response method we are extracting all our json data.
                try {
                    JSONArray itemsArray = response.getJSONArray("items");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        ArrayList<String> authors = new ArrayList<>();
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String language = volumeObj.optString("language");
                        if(language.equals("en")) {
                            String title = volumeObj.optString("title");
                            String subtitle = volumeObj.optString("subtitle");
                            if(volumeObj.has("authors")) {
                                JSONArray authorsArray = volumeObj.getJSONArray("authors");
                                if (authorsArray.length() != 0) {
                                    for (int j = 0; j < authorsArray.length(); j++) {
                                        authors.add(authorsArray.optString(j));
                                    }
                                }
                            }
                            else
                            {
                                authors.add("No author");
                            }

                            String publisher = volumeObj.optString("publisher");
                            String publishedDate = volumeObj.optString("publishedDate");
                            String description = volumeObj.optString("description");
                            int pageCount = volumeObj.optInt("pageCount");
                            JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                            String thumbnail = imageLinks.optString("thumbnail");
                            String previewLink = volumeObj.optString("previewLink");
                            String infoLink = volumeObj.optString("infoLink");
                            JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");
                            //String buyLink = saleInfoObj.optString("buyLink");

                            // after extracting all the data we are
                            // saving this data in our modal class.
                            String base = thumbnail.substring(5);
                            thumbnail = "https:" + base;
                            Book book = new Book(i, title, subtitle, authors, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, language);


                            // below line is use to pass our modal
                            // class in our array list.
                            bookInfoArrayList.add(book);
                            // below line is use to pass our
                            // array list in adapter class.
                        }
                        BookListAdapter adapter = new BookListAdapter(bookInfoArrayList, mainActivity, new BookListAdapter.ICLickItemListener() {
                            @Override
                            public void onClickBook(Book book) {
                                mainActivity.gotoDetailFragment("object",book);
                            }
                        });

                        // below line is use to add linear layout
                        // manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
                        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.idRVBooks);

                        // in below line we are setting layout manager and
                        // adapter to our recycler view.
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(mainActivity, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(mainActivity, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(booksObjrequest);
    }
}

