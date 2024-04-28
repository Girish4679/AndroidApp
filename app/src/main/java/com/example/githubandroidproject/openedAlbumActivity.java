package com.example.githubandroidproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class openedAlbumActivity extends AppCompatActivity
{
    private String albumName;
    private Album album;

    RecyclerView photoListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openalbum);



    }

    private void displayPhotos() {
        List<Photo> photos = new ArrayList<>();
        if (album != null) {
            photos.addAll(album.getPhotos());
        }
        PhotoAdapter adapter = new PhotoAdapter(photos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }


}
