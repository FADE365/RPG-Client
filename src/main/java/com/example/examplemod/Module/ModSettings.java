package com.example.examplemod.Module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static com.example.examplemod.Client.modules;
import static com.example.examplemod.Module.Module.mc;

public class ModSettings {
    public Map<String, Boolean> moduleStates = new HashMap<>();
    public Map<Integer, LookPos> savedPositions = new HashMap<>(); // Добавлено сохранение позиций
    public Map<String, Integer> keyBindings = new HashMap<>();

    public static ModSettings loadSettings(File file) {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            ModSettings settings = gson.fromJson(reader, ModSettings.class);

            for (Module module : modules) {
                Boolean isEnabled = settings.moduleStates.get(module.getName());
                Boolean isDisabled = settings.moduleStates.get(module.getName());
                if (isDisabled != null) {
                    module.isDisabled = isDisabled;
                    System.out.println("Loaded " + module.getName() + " isDisabled: " + isDisabled);
                } else {
                    module.isDisabled = false;
                    System.out.println("Loaded " + module.getName() + " isDisabled: false (default)");
                }

                // Загрузка биндов клавиш
                Integer keyCode = settings.keyBindings.get(module.getName());
                if ((keyCode != null) && mc.world == null) {
                    module.keyCode = keyCode;
                    System.out.println("Loaded " + module.getName() + " keyCode: " + keyCode);
                }
            }

            return settings;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModSettings();
    }


    public static void saveSettings(ModSettings settings, File file) {
        try (Writer writer = new FileWriter(file)) {
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
