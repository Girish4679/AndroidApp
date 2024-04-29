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
        //displayPhotos(selected_album);

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

    /*
    private void displayPhotos(Album album) {
        List<Photo> photos = new ArrayList<>();
        if (album != null) {
            photos.addAll(album.getPhotos());
        }
        Log.d("PHOTOS", photos.toString());
        Log.d("PHOTOS", String.valueOf(photos.size()));
        adapter = new PhotoAdapter(this, photos, new PhotoAdapter.OnTagClickListener() {
            @Override
            public void onDeleteClicked(Photo photo) {
                selected_album.removePhoto(photo);
                photos.remove(photo);
                Log.d("DELETEPHOTO", String.valueOf(selected_album.getPhotos().size()));
                saveAlbum("ERROR WHILE DELETING PHOTO");
                // Update the adapter with the modified list of photos
                adapter.updatePhotos(photos);
            }
        }, this);
        //PhotoAdapter adapter = new PhotoAdapter(this,photos,this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }

     */
}

