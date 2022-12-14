package com.example.final_project.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends  RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    private Context context;
    private static final String THUMBNAIL_URI_KEY = "smallThumbnail";
    private ICLickItemListener icLickItemListener;
    public interface ICLickItemListener{
        void onClickBook(Book book);
    }
    // creating variables for arraylist and context.
    private ArrayList<Book> bookInfoArrayList;

    // creating constructor for array list and context.
    public BookListAdapter(ArrayList<Book> bookInfoArrayList, Context context,ICLickItemListener listener) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.context = context;
        this.icLickItemListener =listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

        // inside on bind view holder method we are
        // setting ou data to each UI component.
        Book book = bookInfoArrayList.get(position);
        String shortTitle;
        if(book.getTitle().length()>45)
        {
            shortTitle = book.getTitle().substring(0,45)+ " ...";
        }
        else
        {
            shortTitle = book.getTitle();
        }
        holder.nameTV.setText(shortTitle);
        holder.AuthorTV1.setText(book.showAuthors(1));
        holder.AuthorTV2.setText(book.showAuthors(2));
        if(book.getPageCount()==0)
            holder.pageCountTV.setText("No. of Pages : N/A");
        else
            holder.pageCountTV.setText("No. of Pages : " + book.getPageCount());
        holder.dateTV.setText(book.getPublishedDate());
        // below line is use to set image from URL in our image view.
        Picasso.get().load(book.getThumbnail()).into(holder.bookIV);
        // below line is use to add on click listener for our item of recycler view.
        holder.bookIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icLickItemListener.onClickBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return bookInfoArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        // below line is use to initialize
        // our text view and image views.
        private TextView nameTV, AuthorTV1,AuthorTV2, pageCountTV, dateTV;
        private ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idBookTitle);
            AuthorTV1 = itemView.findViewById(R.id.idAuthor1);
            AuthorTV2 = itemView.findViewById(R.id.idAuthor2);
            pageCountTV = itemView.findViewById(R.id.idPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
        }
    }
}
