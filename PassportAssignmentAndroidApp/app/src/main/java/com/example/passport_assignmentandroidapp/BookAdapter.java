// BookAdapter.java
package com.example.passport_assignmentandroidapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passport_assignmentandroidapp.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cell, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewTitle.setText(book.getBooksName());
        holder.textViewAuthor.setText(book.getAuthor());
        holder.textViewRating.setText(book.getRating());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBooks(List<Book> books) {
        this.bookList = books;
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewAuthor;
        TextView textViewRating;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
