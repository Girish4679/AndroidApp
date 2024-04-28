package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    Uri selectedFile;
    private Set<Tag> tagList;
    private List<Photo> addedPhotos = new ArrayList<>();
    private List<Album> albums;


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

        createAlbumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlbumText.setText("");
            }
        });
        tagValueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagValueText.setText("");
            }
        });
        openAlbumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbumText.setText("");
            }
        });
        deleteAlbumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlbumText.setText("");
            }
        });
        renameAlbumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renameAlbumText.setText("");
            }
        });


        if(choosePhoto!=null) {
            choosePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });
        }
        tagKeys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Tag: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> keys = new ArrayList<>();
        keys.add("Location");
        keys.add("Person");
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keys);
        adap.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        tagKeys.setAdapter(adap);
        if(addTag!=null)
        {
            addTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String tagKey = (String)tagKeys.getSelectedItem();
                    String tagVal = String.valueOf(tagValueText);
                    if(tagKey != null && tagVal !=null)
                        tagList.add(new Tag(tagKey, tagVal));
                    tagValueText.setText("");
                }
            });
        }
        if(addPhoto!=null)
        {
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        if (selectedFile != null)
                        {
                            Photo photo = new Photo(selectedFile.toString());
                            for(Tag t: tagList){
                                photo.addTag(t);
                            }
                            tagList.clear();
                            addedPhotos.add(photo);
                        }
                        Toast.makeText(MainActivity.this, "Photo Added!" + selectedFile, Toast.LENGTH_SHORT).show();
                        selectedFile = null;
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, "One of the fields is null!", Toast.LENGTH_SHORT).show();
                    }
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
            selectedFile = data.getData();
        }
    }

}