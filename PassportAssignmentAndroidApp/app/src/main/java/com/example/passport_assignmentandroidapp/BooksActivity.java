package com.example.passport_assignmentandroidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        recyclerView = findViewById(R.id.recyclerViewBooks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the adapter here
        bookAdapter = new BookAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookAdapter);

        // Fetch books asynchronously
        new FetchBooksTask().execute();
    }

    private class FetchBooksTask extends AsyncTask<Void, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(Void... voids) {
            List<Book> books = new ArrayList<>();

            try {

                URL url = new URL("http://10.0.2.2:3000/books");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // Set up the request method
                httpURLConnection.setRequestMethod("GET");

                // Set the session ID in the request headers
                String sessionId = getSessionIdFromSharedPreferences();
                httpURLConnection.setRequestProperty("Cookie", sessionId);

                // Read the response from the server
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    String jsonResponse = response.toString();
                    JSONArray jsonArray = new JSONArray(jsonResponse);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonBook = jsonArray.getJSONObject(i);
                        String title = jsonBook.getString("BooksName");
                        String author = jsonBook.getString("Author");
                        String rating = jsonBook.getString("Rating");

                        books.add(new Book(title, author, rating));
                    }

                    reader.close();
                } else {
                    switch (responseCode) {
                        case HttpURLConnection.HTTP_NOT_FOUND:
                            // Handle 404 Not Found
                            showToast("Server returned 404 Not Found");
                            break;
                        case HttpURLConnection.HTTP_INTERNAL_ERROR:
                            // Handle 500 Internal Server Error
                            showToast("Server returned 500 Internal Server Error");
                            break;
                        default:
                            // Handle other response codes
                            showToast("Unexpected response code: " + responseCode);
                            break;
                    }
                }

                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return books;
        }

        private String getSessionIdFromSharedPreferences() {
            SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            return preferences.getString("sessionId", null);
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            // Update UI with the fetched books
            bookAdapter = new BookAdapter(books);
            recyclerView.setAdapter(bookAdapter);

            // Notify the adapter that the dataset has changed
            bookAdapter.notifyDataSetChanged();
        }

        private void showToast(String message) {
            runOnUiThread(() -> Toast.makeText(BooksActivity.this, message, Toast.LENGTH_SHORT).show());
        }
    }
}
