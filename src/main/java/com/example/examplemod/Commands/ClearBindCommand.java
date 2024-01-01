package com.example.examplemod.Commands;

import com.example.examplemod.Commands.UtilsCo.Command;
import com.example.examplemod.Module.ModSettings;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@SideOnly(Side.CLIENT)
public class ClearBindCommand extends Command {

    private static final File settingsFile = new File("mod_settings.json"); // Файл для сохранения настроек

    public ClearBindCommand() {
        super("clearbind");
    }

    @Override
    public String usage() {
        return TextFormatting.RED + "\u00A7l" + "Usage: .clearbind";
    }

    @Override
    public String execute(String[] args) {
        if (args.length != 1) {
            return usage();
        }

        // Очищаем бинды клавиш в настройках
        ModSettings settings = ModSettings.loadSettings(settingsFile);
        settings.keyBindings.clear();
        ModSettings.saveSettings(settings, settingsFile);

        return TextFormatting.GREEN + "\u00A7l" + "Cleared all key bindings.";
    }
}

