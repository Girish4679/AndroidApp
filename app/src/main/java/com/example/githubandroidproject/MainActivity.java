package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    Button createAlbum;
    Button choosePhoto;
    Button addPhoto;
    Button addTag;
    Button openAlbum;
    Button deleteAlbum;
    Button renameAlbum;
    TextView createAlbumText;
    TextView tagValueText;
    Spinner tagKeys;
    TextView openAlbumText;
    TextView deleteAlbumText;
    TextView renameAlbumText;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAlbum = findViewById(R.id.createAlbumButton);
        choosePhoto = findViewById(R.id.choosePhotoButton);
        addPhoto = findViewById(R.id.addPhotoButton);
        addTag = findViewById(R.id.addTagButton);
        openAlbum = findViewById(R.id.openAlbumButton);
        deleteAlbum = findViewById(R.id.deleteAlbumButton);
        renameAlbum = findViewById(R.id.renameAlbumButton);
        createAlbumText = findViewById(R.id.createAlbumText);
        tagValueText = findViewById(R.id.tagValueId);
        openAlbumText = findViewById(R.id.openAlbumText);
        deleteAlbumText = findViewById(R.id.deleteAlbumText);
        renameAlbumText = findViewById(R.id.renameAlbumText);
        tagKeys = findViewById(R.id.tagKeysId);

        if(choosePhoto!=null) {
            choosePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });
        }



        openAlbum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, openedAlbumActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Retrieve the selected image URI
            Uri selectedImageUri = data.getData();
            // Do something with the selected image URI
        }
    }

}