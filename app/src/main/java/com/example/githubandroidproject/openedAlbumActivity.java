package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class openedAlbumActivity extends AppCompatActivity implements SlideshowInterface
{

    private RecyclerView photoListView;
    Button photoAdd, photoMove, back;
    EditText destination;
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
        destination = findViewById(R.id.moveDestinationID);


        Intent intent = getIntent();
        try{
            albums = storeUtility.loadAlbums();
            Log.d("ALBUM LOADED","ALBUM LOADED");
        } catch (IOException e) {
            Log.d("ALBUM DIDNT LOAD","ALBUM Didnt load");
            albums = (List<Album>) intent.getSerializableExtra("albums_list");
        }
        selected_album = (Album) intent.getSerializableExtra("selected_album");
        String selected_name = selected_album.getName();
        for(Album album : albums){
            if(album.getName().equals(selected_name)){
                selected_album = album;
            }
        }
        Log.d("SELECTED_ALBUM", selected_album.getName());
        displayPhotos(selected_album);


        if(photoAdd!=null)
        {
            photoAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                            | Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
        if(photoMove!=null)
        {
            photoMove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String destinationAlbumName = destination.getText().toString();
                    Log.d("INONCLICK", destinationAlbumName);
                    if (containsAlbum(albums, destinationAlbumName)) {
                        Log.d("MOVEDALBUM", String.valueOf(albums.size()));
                        // Get the destination album from the list of albums
                        Album destinationAlbum = null;
                        for (Album album : albums) {
                            if (album.getName().equals(destinationAlbumName)) {
                                destinationAlbum = album;
                                break;
                            }
                        }
                        // Move selected photos to the destination album
                        if (destinationAlbum != null) {
                            for (Photo p : selected_album.getPhotos()) {
                                if (p.isSelected()) {
                                    // Remove photo from the current album
                                    selected_album.removePhoto(p);
                                    // Add photo to the destination album
                                    destinationAlbum.addPhoto(p);
                                }
                            }
                            // Save changes
                            saveAlbum("Error while moving photos");

                            // Update UI
                            displayPhotos(selected_album);
                        } else {
                            // Handle case where destination album is not found
                            Toast.makeText(openedAlbumActivity.this, "Destination album not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle case where destination album name is invalid
                        Toast.makeText(openedAlbumActivity.this, "Invalid destination album name", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedFile = data.getData();
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(selectedFile, takeFlags);
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
                for(int i = 0;i<albums.size();i++){
                    if(albums.get(i).getName().equals(selected_album.getName())){
                        albums.get(i).setPhotos(photos);
                    }
                }
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

    public boolean containsAlbum(List<Album> albums, String albumName) {
        for (Album album : albums) {
            if (album.getName().equalsIgnoreCase(albumName)) {
                return true; // Found a matching album
            }
        }
        return false; // No matching album found
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
