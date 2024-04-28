package com.example.githubandroidproject;
import android.content.Context;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class StoreUtility {
    private String filename = "albums_data.json";
    public static void saveObjToFile(Context context, Object object, String fileName) throws IOException {
        Gson gson = new Gson();
        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter isr = new OutputStreamWriter(fos);
            gson.toJson(object, isr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadObjFromFile(Context context, String fileName, Type type){
        Gson gson = new Gson();
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            return gson.fromJson(isr, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
