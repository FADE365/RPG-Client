package com.example.examplemod.Module.RENDER;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;


public class FullBright extends Module {

    public FullBright() {
        super("FullBright", Keyboard.KEY_NONE, Category.RENDER);
    }

    private float OldGamma = mc.gameSettings.gammaSetting;
    public static float FullBrightGamma;

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.gameSettings.gammaSetting = 10.0f;
            FullBrightGamma = mc.gameSettings.gammaSetting;
            super.onEnable();
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.gameSettings.gammaSetting = OldGamma;
            FullBrightGamma = mc.gameSettings.gammaSetting;
            super.onDisable();
        }
    }
}
