package org.specialiststeak.scraper.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public final class GsonUtils {
    public static void writeHashMapToFile(HashMap<String, String> map, String filepathToWriteTo) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(map);
        try (FileWriter writer = new FileWriter(filepathToWriteTo)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
