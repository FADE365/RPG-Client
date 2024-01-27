package com.example.examplemod.Module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.examplemod.Client.DIRECTORY_PATH;

public class ModSettings {
    public Map<String, Boolean> moduleStates = new HashMap<>();
    public Map<Integer, LookPos> savedPositions = new HashMap<>(); // Добавлено сохранение позиций
    public Map<String, Integer> keyBindings = new HashMap<>();

    public static ModSettings loadSettings(File file) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File settingsFile = new File(directory, file.getName());
        if (!settingsFile.exists()) {
            return new ModSettings();
        }

        try (Reader reader = new FileReader(settingsFile)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ModSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ModSettings();
        }
    }


    public static void saveSettings(ModSettings settings, File file) {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File settingsFile = new File(directory, file.getName());
        try (Writer writer = new FileWriter(settingsFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Вложенный класс для хранения позиций
    public static class LookPos {
        public int x;
        public int y;
        public int z;

         public LookPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
