package com.example.githubandroidproject;

import android.content.Intent;
import android.os.Bundle;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

public class searchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();

        List<Photo> photos = intent.getParcelableArrayListExtra("photos");

        // Use the photos list for displaying or processing search results
        if (photos !=  null) {
            // ... use photos for search functionality
        } else {
            // Handle the case where photos are not passed
        }
    }
}

