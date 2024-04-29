package com.example.githubandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class searchActivity extends AppCompatActivity {

    private List<Album> albums;
    private List<Photo> selected_photos;
    private RecyclerView photoListView;

    Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d("AM I EVEN HERE", "WE HERE");

        photoListView = findViewById(R.id.searchPhotoViewID);
        back = findViewById(R.id.backButtonSearch);

        Intent intent = getIntent();
        albums = (List<Album>) intent.getSerializableExtra("albums_list");
        selected_photos = (List<Photo>) intent.getSerializableExtra("selected_photos");
        displayPhotos();

        if(back!=null)
        {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(searchActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void displayPhotos() {
        //Log.d("PHOTOS", photos.toString());
        //Log.d("PHOTOS", String.valueOf(photos.size()));
        searchAdapter adapter = new searchAdapter(this,selected_photos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }
}

