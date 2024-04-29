package com.example.githubandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class openedAlbumActivity extends AppCompatActivity
{

    RecyclerView photoListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openalbum);
        photoListView = findViewById(R.id.photoView);

        Intent intent = getIntent();
        List<Album> albums = (List<Album>) intent.getSerializableExtra("albums_list");
        Album selected_album = (Album) intent.getSerializableExtra("selected_album");
        Log.d("SELECTED_ALBUM", selected_album.getName());
        //displayPhotos(selected_album);




    }
    /*
    private void displayPhotos(Album album) {
        List<Photo> photos = new ArrayList<>();
        if (album != null) {
            photos.addAll(album.getPhotos());
        }
        Log.d("PHOTOS", photos.toString());
        PhotoAdapter adapter = new PhotoAdapter(this,photos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }

     */




}
