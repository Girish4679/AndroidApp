package com.example.githubandroidproject;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
public class StoreUtility {
    private static Gson gson = new Gson();
    private String filename = "albums_data.json";
    public static void saveObjToFile(Object object, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object, writer);
        }
    }

}
