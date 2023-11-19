package com.example.passport_assignmentandroidapp;

public class Book {
    private String BooksName;
    private String author;
    private String rating;

    public Book(String BooksName, String author, String rating) {
        this.BooksName = BooksName;
        this.author = author;
        this.rating = rating;
    }

    public String getBooksName() {
        return BooksName;
    }

    public String getAuthor() {
        return author;
    }

    public String getRating() {
        return rating;
    }
}


