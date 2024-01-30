package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.ModSettings;
import com.example.examplemod.Module.Module;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ModuleList extends Module {

    public static String Positions;
    private static byte cnt = 0;
    public ModuleList() {
        super("Module List" + " | " + Positions, Keyboard.KEY_NONE, Category.CLIENT);
        this.setToggled(true);
    }

    @Override
    public void onDisable() {
        changePosition();
    }

    public void changePosition() {
        // Создаем экземпляр ModSettings
        ModSettings settings = ModSettings.loadSettings(new File("mod_settings.json"));

        if (cnt != 3) cnt += 1; else cnt = 1;
        switch (cnt) {
            case 1:
                this.setToggled(true);
                Positions = "Right";
                settings.moduleListPosition = Positions;
                break;
            case 2:
                this.setToggled(true);
                Positions = "Left";
                settings.moduleListPosition = Positions;
                break;
            case 3:
                this.setToggled(true);
                Positions = "off";
                settings.moduleListPosition = Positions;
                break;
            default:
                break;
        }
        settings.moduleListPosition = Positions;
        this.setName("Module List" + " | " + Positions);

        // Сохранение настроек
        ModSettings.saveSettings(settings, new File("mod_settings.json"));
    }

    public static String getPositions() {
        return Positions;
    }
    public static void setPositions(String positions) {
        Positions = positions;
    }

    @Override
    public void onEnable() {
        this.setName("Module List" + " | " + Positions);
        ModSettings settings = ModSettings.loadSettings(new File("mod_settings.json"));
        settings.moduleListPosition = this.Positions;
        ModSettings.saveSettings(settings, new File("mod_settings.json"));
        super.onEnable();
    }
}
