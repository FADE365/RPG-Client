package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class PanelBackGround extends Module {
    public PanelBackGround() {
        super("Panel Background", Keyboard.KEY_NONE, Category.CLIENT);
        this.setToggled(true);
    }
    private static Color Purpure = new Color(0x0C004C1);
    private static Color White = new Color(0xFFFFFF);
    private static Color Black = new Color(0x000000);
    private static Color Cleared = new Color(0x67000000, true);

    public static Color thisColor;

    public static void setThisColor(Color setColor) {
        thisColor = setColor;
    }
    public static Color getThisColor() {
        return thisColor;
    }

    public static Color getWPColor() {
        if (getThisColor().hashCode() == Purpure.hashCode()) {
            return White;
        } else {
            return Purpure;
        }
    }

    @Override
    public void onEnable() {
        setThisColor(Purpure);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        setThisColor(Cleared);
        super.onDisable();
    }
}
