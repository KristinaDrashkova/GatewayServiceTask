package com.musala.gateway.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonParser {
    private Gson gson;

    private FileReader fileReader;

    @Autowired
    public JsonParser(FileReader fileReader) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        this.fileReader = fileReader;
    }

    public <T> T getObject(Class<T> tClass, String path) {
        T obj = null;
        String json = this.fileReader.readFile(path);
        obj = this.gson.fromJson(json, tClass);
        return obj;
    }

    public <T> void writeObject(T tClass, String path) {
        String content = this.gson.toJson(tClass);
        try {
            this.fileReader.writeFile(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
