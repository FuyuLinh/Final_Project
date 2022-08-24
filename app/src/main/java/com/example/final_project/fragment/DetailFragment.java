package com.example.final_project.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getName();
    private ArrayList<String> authors;
    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV;
    Button backButton,previewBtn, buyBtn;
    private ImageView bookIV;
    View view;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        titleTV = view.findViewById(R.id.idTVTitle);
        subtitleTV = view.findViewById(R.id.idTVSubTitle);
        publisherTV = view.findViewById(R.id.idTVpublisher);
        descTV = view.findViewById(R.id.idTVDescription);
        pageTV = view.findViewById(R.id.idTVNoOfPages);
        publishDateTV = view.findViewById(R.id.idTVPublishDate);
        previewBtn = view.findViewById(R.id.idBtnPreview);
        backButton = view.findViewById(R.id.backButton);
        buyBtn = view.findViewById(R.id.idBtnBuy);
        bookIV = view.findViewById(R.id.idIVbook);

        mainActivity = (MainActivity)getActivity();
        Bundle bundleReceive = getArguments();
        if(bundleReceive!= null) {
            Book book = (Book) bundleReceive.get("object");
            if(book!=null) {
                titleTV.setText(book.getTitle());
                subtitleTV.setText(book.getSubtitle());
                publisherTV.setText(book.getPublisher());
                publishDateTV.setText("Published On : " + book.getPublishedDate());
                descTV.setText(book.getDescription());
                pageTV.setText("No Of Pages : " + book.getPageCount());
                Picasso.get().load(book.getThumbnail()).into(bookIV);
                previewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (book.getPreviewLink().isEmpty()) {
                            // below toast message is displayed when preview link is not present.
                            Toast.makeText(mainActivity, "No preview Link present", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // if the link is present we are opening
                        // that link via an intent.
                        Uri uri = Uri.parse(book.getPreviewLink());
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                });
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
}