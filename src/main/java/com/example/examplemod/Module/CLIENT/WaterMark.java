package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

public class WaterMark extends Module {
    private static boolean WaterMark;
    public WaterMark() {
        super("WaterMark", Keyboard.KEY_NONE, Category.CLIENT);
        this.setToggled(true);
    }

    @Override
    public void onEnable() {
        WaterMark = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        WaterMark = false;
        super.onDisable();
    }
    public static boolean getWaterMark() {
        return WaterMark;
    }
    public static void setWaterMark(boolean state) {
        WaterMark = state;
    }
}
