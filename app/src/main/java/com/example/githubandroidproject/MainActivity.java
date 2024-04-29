package com.example.githubandroidproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
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
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements AlbumClickInterface{

    Button createAlbum;
    Button choosePhoto;
    Button addPhoto;
    Button addTag;
    Button deleteAlbum;
    Button renameAlbum;
    Button search;
    EditText createAlbumText;
    EditText tagValueText;
    Spinner tagKeys;
    Spinner keyOne, keyTwo;
    EditText deleteAlbumText;
    EditText renameAlbumText;
    EditText newNameAlbumText;
    EditText conjunctionText;
    AutoCompleteTextView autoTextOne, autoTextTwo;

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
        search = findViewById(R.id.searchID);
        createAlbumText = findViewById(R.id.createAlbumText);
        tagValueText = findViewById(R.id.tagValueId);
        deleteAlbumText = findViewById(R.id.deleteAlbumText);
        renameAlbumText = findViewById(R.id.renameAlbumText);
        renameAlbumText.setInputType(InputType.TYPE_NULL);
        newNameAlbumText = findViewById(R.id.newNameAlbumText);
        newNameAlbumText.setInputType(InputType.TYPE_NULL);
        conjunctionText = findViewById(R.id.conjunctionID);
        conjunctionText.setInputType(InputType.TYPE_NULL);
        tagKeys = findViewById(R.id.tagKeysId);
        keyOne = findViewById(R.id.spinnerTwo);
        keyTwo = findViewById(R.id.spinnerThree);

        autoTextOne = findViewById(R.id.autoOne);
        autoTextOne.setInputType(InputType.TYPE_NULL);
        autoTextTwo = findViewById(R.id.autoTwo);
        autoTextTwo.setInputType(InputType.TYPE_NULL);


        albumView = findViewById(R.id.albumID);
        recyclerAdapter = new RecyclerAdapter(this,this);
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
                        deleteAlbumText.setText("");
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
                        renameAlbumText.setText("");
                        newNameAlbumText.setText("");
                    }
                }
            });
        }
        if(choosePhoto!=null) {
            choosePhoto.setOnClickListener(new View.OnClickListener() {
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

        keyOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        keyTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
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
        keyOne.setAdapter(adap);
        keyTwo.setAdapter(adap);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getTagList());
        autoTextOne.setAdapter(adapter);
        autoTextTwo.setAdapter(adapter);

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
                    tagKeys.setSelection(0);
                    tagValueText.setText("");
                }
            });
        }
        if(search!=null)
        {
            search.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String tagName1 = (String) keyOne.getSelectedItem();
                    String tagName2 = (String) keyTwo.getSelectedItem();
                    String tagVal1 = autoTextOne.getText().toString();
                    String tagVal2 = autoTextTwo.getText().toString();
                    String conjunction = conjunctionText.getText().toString();
                    List<Photo> photos = new ArrayList<Photo>();
                    for(Album a : albums)
                    {
                        for(Photo p : a.getPhotos())
                        {
                            photos.add(p);
                        }
                    }
                    List<Photo> selectedPhotos = new ArrayList<>();
                    if(!tagName1.equals("") && !tagVal1.equals("") && !tagName2.equals("") && !tagVal2.equals(""))
                    {
                        for(Photo p : photos)
                        {
                            if(conjunction.equalsIgnoreCase("and"))
                            {
                                boolean hasTag1 = false;
                                boolean hasTag2 = false;
                                for(Tag t : p.getTags())
                                {
                                    if(t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagVal1))
                                    {
                                        hasTag1 = true;
                                    }
                                    else if(t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagVal2))
                                    {
                                        hasTag2 = true;
                                    }
                                    if(hasTag1 && hasTag2)
                                    {
                                        selectedPhotos.add(p);
                                        break;
                                    }
                                }
                            }
                            else if(conjunction.equalsIgnoreCase("or"))
                            {
                                for(Tag t : p.getTags())
                                {
                                    if(t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagVal1))
                                    {
                                        selectedPhotos.add(p);
                                    }
                                    if(t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagVal2))
                                    {
                                        selectedPhotos.add(p);
                                    }
                                }
                            }
                        }
                        if(selectedPhotos.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "No Photos in Range!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            searchPhotos(selectedPhotos);
                        }
                    }
                    else if((!tagName1.equals("") && !tagVal1.equals("")) || (!tagName2.equals("") && !tagVal2.equals("")))
                    {
                        for(Photo p : photos)
                        {
                            for(Tag t : p.getTags())
                            {
                                if((!tagName1.equals("") && !tagVal1.equals("") && t.getName().equalsIgnoreCase(tagName1) && t.getValue().equalsIgnoreCase(tagVal1))
                                        || (!tagName2.equals("") && !tagVal2.equals("") && t.getName().equalsIgnoreCase(tagName2) && t.getValue().equalsIgnoreCase(tagVal2)))
                                {
                                    selectedPhotos.add(p);
                                }
                            }
                        }
                        if(selectedPhotos.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "No Photos in Range!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            searchPhotos(selectedPhotos);
                        }
                    }
                    autoTextOne.setText("");
                    autoTextTwo.setText("");
                    keyOne.setSelection(0);
                    keyTwo.setSelection(0);
                    conjunctionText.setText("");
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
                        addedPhotos.clear();
                        albums.add(album);
                        saveAlbum("ERROR WHILE ADDING ALBUM");
                        Log.d("ADDED TO ALBUM",albumName);
                        createAlbumText.setText("");
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
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getContentResolver().takePersistableUriPermission(selectedFile, takeFlags);
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
    @Override
    public void onAlbumClick(Album album){

        Intent intent = new Intent(this, openedAlbumActivity.class);
        intent.putExtra("selected_album", album);
        intent.putExtra("albums_list",(Serializable) this.albums);
        startActivity(intent);
    }
    private ArrayList<String> getTagList() {
        ArrayList<String> tagValues = new ArrayList<>();

        for (Album a : albums) {
            for (Photo p : a.getPhotos()) {
                // Check if p.getTags() returns a collection (e.g., List<Tag>)
                if (p.getTags() instanceof Collection) {
                    // Extract tag values from the collection and add to tagValues
                    for (Tag tag : (Collection<Tag>) p.getTags()) {
                        tagValues.add(tag.getValue()); // Assuming Tag has a getValue() method to get the string value
                    }
                } else if (p.getTags() != null) { // Handle single Tag object (if applicable)
                    tagValues.add(((Tag) p.getTags()).getValue()); // Assuming Tag is a single object
                }
            }
        }

        return tagValues;
    }

    private void searchPhotos(List<Photo> photos) {
        try {
            Log.d("SELECTEDPHOTOS", String.valueOf(photos.size()));
            Intent intent = new Intent(this, searchActivity.class);
            intent.putExtra("selected_photos", (Serializable) photos);
            intent.putExtra("albums_list",(Serializable) this.albums);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("SEARCH_ACTIVITY_ERROR", "Error launching search activity", e);
        }
    }






}