package com.example.githubandroidproject;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class openedAlbumActivity extends AppCompatActivity implements SlideshowInterface
{

    RecyclerView photoListView;
    List<Album> albums;
    Album selected_album;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openalbum);
        photoListView = findViewById(R.id.photoView);

        Intent intent = getIntent();
        albums = (List<Album>) intent.getSerializableExtra("albums_list");
        selected_album = (Album) intent.getSerializableExtra("selected_album");
        Log.d("SELECTED_ALBUM", selected_album.getName());
        displayPhotos(selected_album);


    }

    private void displayPhotos(Album album) {
        List<Photo> photos = new ArrayList<>();
        if (album != null) {
            photos.addAll(album.getPhotos());
        }
        Log.d("PHOTOS", photos.toString());
        PhotoAdapter adapter = new PhotoAdapter(this,photos,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }

    @Override
    public void onPhotoClick(List<Photo> photos, int index){

        Intent intent = new Intent(this, SlideshowActivity.class);
        intent.putExtra("photos", (Serializable) photos);
        intent.putExtra("albums_list",(Serializable) albums);
        intent.putExtra("album", (Serializable) selected_album);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}
