package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class openedAlbumActivity extends AppCompatActivity
{

    private RecyclerView photoListView;
    Button photoAdd, photoMove, back;
    Uri selectedFile;
    private Album selected_album;
    private StoreUtility storeUtility;
    private List<Album> albums;
    private PhotoAdapter adapter;

    private static final int PICK_IMAGE_REQUEST = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openalbum);
        photoListView = findViewById(R.id.photoView);
        photoAdd = findViewById(R.id.addPhotoID);
        photoMove = findViewById(R.id.movePhotoID);
        storeUtility = new StoreUtility(this);
        back = findViewById(R.id.backID);


        Intent intent = getIntent();
        albums = (List<Album>) intent.getSerializableExtra("albums_list");
        selected_album = (Album) intent.getSerializableExtra("selected_album");
        displayPhotos(selected_album);


        if(photoAdd!=null)
        {
            photoAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);

                }
            });
        }
        if(back!=null)
        {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(openedAlbumActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        /*
        if(photoMove!=null) {
            photoMove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Iterate through selected photos and move them to the desired album
                    for (Photo photo : selectedPhotos) {
                        // Move photo to the desired album
                        // Update your data structure accordingly
                        // For example:
                        selected_album.removePhoto(photo);
                        newAlbum.addPhoto(photo);
                    }

                    // Clear the list of selected photos
                    selectedPhotos.clear();

                    // Save the changes to albums
                    saveAlbum("ERROR WHILE MOVING PHOTOS");

                    // Refresh the RecyclerView
                    displayPhotos(selected_album);
                }
            });
        }

         */


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedFile = data.getData();
            Toast.makeText(openedAlbumActivity.this, "Photo Added!" + selectedFile, Toast.LENGTH_SHORT).show();
            selected_album.addPhoto(new Photo(selectedFile.toString()));
            for (int i = 0; i < albums.size(); i++) {
                if (albums.get(i).getName().equals(selected_album.getName())) {
                    albums.set(i, selected_album);
                    break;
                }
            }
            saveAlbum("ERROR WHILE ADDING PHOTO");
            displayPhotos(selected_album);
        }
    }

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
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(adapter);
    }

    private void saveAlbum(String message){
        try {
            storeUtility.saveAlbums(albums);
            //recyclerAdapter.setAlbums(albums);
        }catch(IOException e){
            e.printStackTrace();
            Log.d("STORE ERROR",message);
            return;
        }
    }
    private int containsName(String name){
        for(int i = 0; i < albums.size();i++){
            if(albums.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }










}
