package com.example.final_project.Adapter;

import com.squareup.picasso.Picasso;
import com.example.final_project.model.Book;
import com.example.final_project.R;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.PostViewHolder> {
    private Context context;
    private static final String THUMBNAIL_URI_KEY = "smallThumbnail";
    private List<Book> data = new ArrayList<>();
    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        final Book book = data.get(position);
        final Book.BookInfo info = book.getInfo();
        if (info != null) {
//            Random rnd = new Random();
//            int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//            holder.parent.setCardBackgroundColor(currentColor);

            //Title
            holder.title.setText(info.getTitle());

            //Authors
            if (info.getAuthors() != null) {
                holder.authors.setText(TextUtils.join(", ", info.getAuthors()));
            } else {
                holder.authors.setText("Authors Unavailable");
            }

            //ImageLinks
            if (info.getImageLinks() != null) {
                holder.path = info.getImageLinks().get(THUMBNAIL_URI_KEY);
                Picasso.get()
                        .load(holder.path)
                        .centerCrop()
                        .fit()
                        .into(holder.image);

            }
        }
    }
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new PostViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(Context context, List<Book> data) {
        this.data = data;
        notifyDataSetChanged();
        this.context = context;
    }

    public List<Book> getData() {
        return data;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView authors;
        public TextView description;
        public String path;
        public CardView parent;


        public PostViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            authors = itemView.findViewById(R.id.authors);
            parent = itemView.findViewById(R.id.item_root);


        }
    }
}
