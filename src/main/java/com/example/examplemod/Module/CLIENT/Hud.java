package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Client;
import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Hud extends Module {
    public static int bindKey = Keyboard.KEY_RSHIFT; // Клавиша бинда по умолчанию

    public Hud() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.CLIENT);
    }


    @Override
    public void onEnable() {
        if (!Panic.isPanic) {
            Minecraft.getMinecraft().displayGuiScreen(Client.clickGui);
            super.onEnable();
            toggle();
        }
    }


    public static void setBindKey(int key) {
        bindKey = key;
    }

    public static int getBindKey() {
        return bindKey;
    }
}

