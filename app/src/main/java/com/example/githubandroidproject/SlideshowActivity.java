package com.example.githubandroidproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class SlideshowActivity extends AppCompatActivity {
    private List<Photo> photos = new ArrayList<>();
    private int currentPhotoIndex;
    private ImageView photoImageView;
    private Spinner tagNameSpinner;
    private EditText tagValueEditText;
    private Photo currentPhoto;
    private List<Album> albums;
    private Album album;
    StoreUtility storeUtility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        photoImageView = findViewById(R.id.photoImageView);
        tagNameSpinner = findViewById(R.id.tagNameSpinner);
        tagValueEditText = findViewById(R.id.tagValueEditText);
        Button addTagButton = findViewById(R.id.addTagButton);
        Button removeTagButton = findViewById(R.id.removeTagButton);
        Button previousButton = findViewById(R.id.previousButton);
        Button nextButton = findViewById(R.id.nextButton);
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Location");
        keys.add("Person");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagNameSpinner.setAdapter(adapter);

        storeUtility = new StoreUtility(this);

        Intent intent = getIntent();
        photos = (List<Photo>) intent.getSerializableExtra("photos");
        albums = (List<Album>) intent.getSerializableExtra("albums_list");
        album = (Album) intent.getSerializableExtra("album");
        currentPhotoIndex = intent.getIntExtra("index",0);


        displayPhoto(currentPhotoIndex);

        previousButton.setOnClickListener(v -> navigatePhoto(-1));
        nextButton.setOnClickListener(v -> navigatePhoto(1));
        addTagButton.setOnClickListener(v -> addTag());
        removeTagButton.setOnClickListener(v -> removeTag());
    }

    private void displayPhoto(int index) {
        if (index >= 0 && index < photos.size()) {
            currentPhoto = photos.get(index);
            try {
                InputStream inputStream = getContentResolver().openInputStream(Uri.parse(currentPhoto.getPath()));
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                photoImageView.setImageBitmap(bitmap);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    private void navigatePhoto(int direction) {
        int newIndex = currentPhotoIndex + direction;
        if (newIndex >= 0 && newIndex < photos.size()) {
            currentPhotoIndex = newIndex;
            displayPhoto(currentPhotoIndex);
        } else {
            Toast.makeText(this, "No more photos in this direction", Toast.LENGTH_SHORT).show();
        }
    }

    private void addTag() {
        String tagName = tagNameSpinner.getSelectedItem().toString();
        String tagValue = tagValueEditText.getText().toString();
        if (!tagValue.isEmpty()) {
            Tag newTag = new Tag(tagName, tagValue);
            photos.get(currentPhotoIndex).addTag(newTag);
            albums.get(albums.indexOf(album)).setPhotos(photos);
            saveAlbum("COULDNT ADD TAG");
            Toast.makeText(this, "Tag added", Toast.LENGTH_SHORT).show();
            tagValueEditText.setText(""); // Clear the input field
        } else {
            Toast.makeText(this, "Tag value cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveAlbum(String message){
        try {
            storeUtility.saveAlbums(albums);
        }catch(IOException e){
            e.printStackTrace();
            Log.d("STORE ERROR",message);
            return;
        }
    }
    private void removeTag() {
        String tagName = tagNameSpinner.getSelectedItem().toString();
        String tagValue = tagValueEditText.getText().toString();
        if (!tagValue.isEmpty()) {
            Tag newTag = new Tag(tagName, tagValue);

            for(Tag tag : photos.get(currentPhotoIndex).getTags()){
                if(tag.equals(newTag)){
                    photos.get(currentPhotoIndex).removeTag(newTag);
                    albums.get(albums.indexOf(album)).setPhotos(photos);
                    saveAlbum("COULDNT ADD TAG");
                    Toast.makeText(this, "Tag removed", Toast.LENGTH_SHORT).show();
                    tagValueEditText.setText(""); // Clear the input field
                    return;
                }
            }
            Toast.makeText(this, "Tag doesn't exist in picture", Toast.LENGTH_SHORT).show();
            tagValueEditText.setText(""); // Clear the input field
        } else {
            Toast.makeText(this, "Tag value cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
