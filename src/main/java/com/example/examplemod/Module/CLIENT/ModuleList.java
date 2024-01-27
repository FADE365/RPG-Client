package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

public class ModuleList extends Module {

    public static String Positions = "Right";
    private static byte cnt = 0;
    public ModuleList() {
        super("Module List" + " | " + Positions, Keyboard.KEY_NONE, Category.CLIENT);
        this.setToggled(true);
    }

    @Override
    public void onDisable() {
        if (cnt != 3) cnt += 1; else cnt = 1;
        switch (cnt) {
            case 1:
                this.setToggled(true);
                Positions = "Right";
            break;
            case 2:
                this.setToggled(true);
                Positions = "Left";
            break;
            case 3:
                this.setToggled(true);
                Positions = "off";
            break;
            default:

            break;
        }
        this.setName("Module List" + " | " + Positions);
    }

    public static String getPositions() {
        return Positions;
    }

    @Override
    public void onEnable() {
        this.setName("Module List" + " | " + Positions);
        super.onEnable();
    }
}
