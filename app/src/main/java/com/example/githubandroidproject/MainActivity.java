package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    Button createAlbum;
    Button choosePhoto;
    Button addPhoto;
    Button addTag;
    Button deleteAlbum;
    Button renameAlbum;
    EditText createAlbumText;
    EditText tagValueText;
    Spinner tagKeys;
    Spinner keyOne, keyTwo;
    EditText deleteAlbumText;
    EditText renameAlbumText;
    EditText newNameAlbumText;
    RecyclerView albumView;
    Uri selectedFile;
    private Set<Tag> tagList = new HashSet<Tag>();
    private List<Photo> addedPhotos = new ArrayList<>();
    private List<Album> albums;
    private RecyclerAdapter recyclerAdapter;
    private StoreUtility storeUtility;

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
        deleteAlbum = findViewById(R.id.deleteAlbumButton);
        renameAlbum = findViewById(R.id.renameAlbumButton);
        createAlbumText = findViewById(R.id.createAlbumText);
        tagValueText = findViewById(R.id.tagValueId);
        deleteAlbumText = findViewById(R.id.deleteAlbumText);
        renameAlbumText = findViewById(R.id.renameAlbumText);
        newNameAlbumText = findViewById(R.id.newNameAlbumText);
        tagKeys = findViewById(R.id.tagKeysId);
        keyOne = findViewById(R.id.multiIDOne);
        keyTwo = findViewById(R.id.multiIDTwo);

        albumView = findViewById(R.id.albumID);
        recyclerAdapter = new RecyclerAdapter(this);
        albumView.setLayoutManager(new LinearLayoutManager(this));
        storeUtility = new StoreUtility(this);

        try{
            albums = storeUtility.loadAlbums();
        }catch(IOException e){
            e.printStackTrace();
            albums = new ArrayList<>();
        }
        recyclerAdapter.setAlbums(albums);
        albumView.setAdapter(recyclerAdapter);
        if(deleteAlbum != null){
            deleteAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String albumName = deleteAlbumText.getText().toString();
                    int index = containsName(albumName);
                    if(index != -1){
                        albums.remove(index);
                        saveAlbum("ERROR WHILE REMOVING ALBUM");
                        Log.d("DELETED ALBUM",albumName);
                    }

                }
            });
        }
        if(renameAlbum!=null)
        {
            renameAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newName = newNameAlbumText.getText().toString();
                    String oldName = renameAlbumText.getText().toString();
                    int index = containsName(oldName);
                    if(index!=-1)
                    {
                        albums.get(index).setName(newName);
                        saveAlbum("ERROR WHILE RENAMING ALBUM");
                        Log.d("RENAMED ALBUM", oldName);
                    }
                }
            });
        }
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
        /*
        keyOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Tag: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        keyTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected Tag: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        ArrayList<String> keys = new ArrayList<>();
        keys.add("Location");
        keys.add("Person");
        ArrayAdapter<String> adap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keys);
        adap.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        tagKeys.setAdapter(adap);
        if (addTag != null) {
            addTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ADDED TO ALBUM",tagValueText.getText().toString());

                    if (tagKeys != null && tagValueText != null) {
                        String tagKey = (String) tagKeys.getSelectedItem();
                        String tagVal = tagValueText.getText().toString();
                        if (tagKey != null && tagVal != null) {
                            tagList.add(new Tag(tagKey, tagVal));
                        }
                    }

                    tagValueText.setText("");
                }
            });
        }

        if(createAlbum != null){
            createAlbum.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String albumName = createAlbumText.getText().toString();
                    if(albumName != null && containsName(albumName)==-1) {
                        Album album = new Album(albumName);
                        for(Photo photo : addedPhotos){
                            album.addPhoto(photo);
                        }
                        addedPhotos = new ArrayList<>();
                        albums.add(album);
                        saveAlbum("ERROR WHILE ADDING ALBUM");
                        Log.d("ADDED TO ALBUM",albumName);
                    }
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
        /*
        if(openAlbum!=null) {
            openAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, openedAlbumActivity.class);
                    startActivity(intent);
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
        }
    }

    /*
    public void displayAlbums() {
        ObservableList<AlbumDisplay> obsList = FXCollections.observableArrayList();
        albums.forEach((name, album) -> {
            int numPics = album.getPhotos().size();
            obsList.add(new AlbumDisplay(album, name, numPics));
        });
        albumListView.setItems(obsList);
    }

     */

    private void saveAlbum(String message){
        try {
            storeUtility.saveAlbums(albums);
            recyclerAdapter.setAlbums(albums);
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