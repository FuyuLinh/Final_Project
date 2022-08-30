package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
    View view;
    MainActivity mainActivity;
    RelativeLayout homeButton, backButton, nextButton;
    TextView pageNum;
    private RequestQueue mRequestQueue;
    private ArrayList<Book> bookInfoArrayList;
    int page;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        homeButton = view.findViewById(R.id.button2);
        backButton = view.findViewById(R.id.button3);
        nextButton = view.findViewById(R.id.button4);
        pageNum = view.findViewById(R.id.PageNum);
        mainActivity = (MainActivity)getActivity();

        Bundle bundleReceive = getArguments();
        if(bundleReceive!= null) {
            String q= (String) bundleReceive.get("query");
            if(q!=null) {
                page = 0;
                pageNum.setText(String.valueOf(page+1));
                getBooksInfo(q,page);
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(page < 4)
                        {
                            page+=1;
                            pageNum.setText(String.valueOf(page+1));
                            getBooksInfo(q,page);
                        }
                    }
                });
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(page > 0)
                        {
                            page-=1;
                            pageNum.setText(String.valueOf(page+1));
                            getBooksInfo(q,page);
                        }
                    }
                });
            }
        }
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragmentManager()!=null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    private void getBooksInfo(String query, int page) {
        query = query + "&maxResults=40&startIndex=" + String.valueOf(40*page);
        // creating a new array list.
        bookInfoArrayList = new ArrayList<>();
        // below line is use to initialize
        // the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(mainActivity);

        // below line is use to clear cache this
        // will be use when our data is being updated.
        mRequestQueue.getCache().clear();
        // below is the url for getting data from API in json format.
        String url = "https://www.googleapis.com/books/v1/volumes?q="+ query + "&key=AIzaSyAIB6Wjw3bm_yiGn1kuFdqbbV7LPa1hzNs";

        // below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(mainActivity);
        JsonObjectRequest booksObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // inside on response method we are extracting all our json data.
                String title,subtitle, publisher, publishedDate;
                try {
                    JSONArray itemsArray = response.getJSONArray("items");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        ArrayList<String> authors = new ArrayList<>();
                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String language = volumeObj.optString("language");
                        // only search book has Language is English
                        if(language.equals("en") && volumeObj.has("imageLinks") && volumeObj.has("title") && volumeObj.has("authors")) {
                            title = getTitle(volumeObj);
                            subtitle = getSubtitle(volumeObj);
                            getAuthor(authors, volumeObj);
                            publisher = getPublisher(volumeObj);
                            publishedDate = getPublishedDate(volumeObj);

                            String description = getDescription(volumeObj);
                            int pageCount = volumeObj.optInt("pageCount");
                            JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                            String thumbnail = imageLinks.optString("thumbnail");
                            String previewLink = volumeObj.optString("previewLink");
                            String infoLink = volumeObj.optString("infoLink");
                            JSONObject saleInfoObj = itemsObj.optJSONObject("saleInfo");

                            String base = thumbnail.substring(5);
                            thumbnail = "https:" + base;
                            Book book = new Book(i, title, subtitle, authors, publisher, publishedDate, description, pageCount, thumbnail, previewLink, infoLink, language);


                            // below line is use to pass our modal
                            // class in our array list.
                            bookInfoArrayList.add(book);
                            // below line is use to pass our
                            // array list in adapter class.
                        }
                    }
                    // Recycler view
                    BookListAdapter adapter = new BookListAdapter(bookInfoArrayList, view.getContext(), new BookListAdapter.ICLickItemListener() {
                        @Override
                        public void onClickBook(Book book) {
                            mainActivity.gotoDetailFragment(book);
                        }
                    });

                    // below line is use to add linear layout
                    // manager for our recycler view.
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
                    RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.idRVBooks);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(adapter);
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
    private void getAuthor(ArrayList<String> authors, JSONObject volumeObj) throws JSONException {
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
            authors.add("No author's info");
        }
    }
    private String getTitle( JSONObject volumeObj) throws JSONException {
        if (volumeObj.has("title")) {
            return volumeObj.optString("title");
        } else
            return "No info";
    }
    private String getSubtitle( JSONObject volumeObj) throws JSONException {
        if (volumeObj.has("subtitle")) {
            return volumeObj.optString("subtitle");
        } else
            return "----------------";
    }
    private String getPublisher( JSONObject volumeObj) throws JSONException {
        if (volumeObj.has("publisher")) {
            return volumeObj.optString("publisher");
        } else
            return "----------------";
    }
    private String getPublishedDate( JSONObject volumeObj) throws JSONException {
        if(volumeObj.has("publishedDate")){
            return volumeObj.optString("publishedDate");
        }
        else
            return "----------------";
    }
    private String getDescription( JSONObject volumeObj) throws JSONException {
        if (volumeObj.has("description")) {
            return volumeObj.optString("description");
        } else
            return "----------------";
    }

}

