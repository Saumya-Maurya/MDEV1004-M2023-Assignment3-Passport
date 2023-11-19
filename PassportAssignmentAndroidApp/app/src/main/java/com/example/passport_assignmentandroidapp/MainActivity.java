package com.example.passport_assignmentandroidapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passport_assignmentandroidapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        TextView btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Perform the login asynchronously
        new LoginTask().execute(username, password);
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            try {
                URL url = new URL("http://10.0.2.2:3000/login");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                // Create the request body
                String requestBody = "username=" + username + "&password=" + password;
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(requestBody);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    handleCookies(httpURLConnection);

                    // Login successful
                    return "Login successful";
                } else {
                    // Handle other response codes
                    return "Login failed, HTTP response code: " + responseCode;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Malformed URL: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "IOException: " + e.getMessage();
            }
        }

        // Add this method to handle cookies
        private void handleCookies(HttpURLConnection httpURLConnection) {
            Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get("Set-Cookie");

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    // Extract the session ID from the cookie
                    String[] parts = cookie.split(";");
                    String sessionId = parts[0];

                    // Save the session ID to SharedPreferences
                    saveSessionIdToSharedPreferences(sessionId);
                }
            }
        }

        private void saveSessionIdToSharedPreferences(String sessionId) {
            SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("sessionId", sessionId);
            editor.apply();
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

            // Redirect to BooksActivity if login was successful
            if (result.equals("Login successful")) {

                Intent intent = new Intent(MainActivity.this, BooksActivity.class);
                startActivity(intent);
            }
        }
    }
}
