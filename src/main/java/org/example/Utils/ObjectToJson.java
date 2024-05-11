package org.example.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ObjectToJson {
    public static String convertToJson(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }
}
