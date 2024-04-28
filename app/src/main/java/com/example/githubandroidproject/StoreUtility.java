package com.example.githubandroidproject;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StoreUtility {
    private static String filename = "albums_data.json";
    private final Context context;
    private final Gson gson;
    public StoreUtility(Context context){
        this.context = context;
        gson = new Gson();
    }

    public void saveAlbums(List<Album> albums) throws IOException{
        String data = gson.toJson(albums);
        FileOutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
        out.write(data.getBytes());
        out.close();
    }

    public List<Album> loadAlbums() throws IOException{
        FileInputStream in = context.openFileInput(filename);
        if(in.available() == 0){
            return new ArrayList<>();
        }
        else{
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            String jsonString = new String(data);
            return gson.fromJson(jsonString, new TypeToken<List<Album>>() {}.getType());
        }
    }

}
