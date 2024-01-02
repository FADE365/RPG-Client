package com.example.examplemod.Module;

import com.example.examplemod.Utils.ChatUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static com.example.examplemod.Module.CLIENT.OnChatModule.OnChat;
import static com.example.examplemod.Module.ModSettings.loadSettings;

public class Module {
    public String name;
    public static String MName;
    public boolean toggled;
    public int keyCode;
    public Category category;
    public static Minecraft mc = Minecraft.getMinecraft();
    public boolean isDisabled;
    public boolean isEnabled;
    public static boolean OnChatDone;


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
        OnChatDone = true;
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        OnChatDone = false;
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

        // Сохранение состояния включенности и выключенности только для этого модуля
        ModSettings settings = loadSettings(new File("mod_settings.json"));
        settings.moduleStates.put(getName(), isEnabled());
        settings.keyBindings.put(getName(), getKey());
        saveSettings(settings, new File("mod_settings.json"));

        // Проверяем состояние OnChat и выводим сообщение в зависимости от него
        if (OnChat && !getName().equals("Hud") && !getName().equals("Rotation") && !getName().equals("HightJump") ) {
            if (toggled) {
                ChatUtils.sendMessage(this.name + " \u00A79Enable!");
            } else {
                ChatUtils.sendMessage(this.name + " \u00A7cDisable!");
            }
        }
    }

    public static void saveSettings(ModSettings settings, File file) {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}