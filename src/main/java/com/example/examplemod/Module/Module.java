package com.example.examplemod.Module;

import com.example.examplemod.Client;
import com.example.examplemod.Module.CLIENT.OnChatModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static com.example.examplemod.Module.ModSettings.loadSettings;

public class Module {
    public String name;
    public static String MName;
    public boolean toggled;
    private static final String DIRECTORY_PATH = "RPG Client";
    public int keyCode;
    public Category category;
    public static Minecraft mc = Minecraft.getMinecraft();
    public boolean isDisabled;
    public boolean isEnabled;
    public static OnChatModule onChatModule;

    public Module(String name, int key, Category c) {
        this.name = name;
        this.keyCode = key;
        this.category = c;
    }

    public boolean isEnabled() {
        return toggled;
    }

    public int getKey() {
        return keyCode;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void setKey(int key) {
        this.keyCode = key;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String n) {
        this.name = n;
    }

    public enum Category {
        COMBAT,
        MOVEMENT,
        PLAYER,
        RENDER,
        MISC,
        CLIENT,
        EXPLOIT,
        AUTODUPE;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
            isEnabled = true;
            isDisabled = false; // Устанавливаем состояние выключенности в false при включении
        } else {
            isEnabled = false;
            isDisabled = true;
            onDisable();
        }

        saveAllModuleStates();

        if (
            !getName().equals("Click  GUI") &&
            !getName().equals("Rotation") &&
            getName().equals("HightJump")
            )
        {
            OnChatModule.SendChat(this.name, toggled);
        }

    }

    public static void saveAllModuleStates() {
        ModSettings settings = loadSettings(new File("mod_settings.json"));

        // Обновляем состояния и бинды для всех модулей
        for (Module module : Client.modules) {
            settings.moduleStates.put(module.getName(), module.isEnabled());
            settings.keyBindings.put(module.getName(), module.getKey());
        }

        saveSettings(settings, new File("mod_settings.json"));
    }

    public static void saveSettings(ModSettings settings, File file) {
        // Создаем объект File для нашей целевой директории
        File directory = new File(DIRECTORY_PATH);
        // Убедимся, что директория существует
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Создаем новый объект File, который указывает на файл внутри целевой директории
        File newFile = new File(directory, file.getName());

        try (Writer writer = new FileWriter(newFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}