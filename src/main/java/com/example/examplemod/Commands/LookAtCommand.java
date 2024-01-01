package com.example.examplemod.Commands;

import com.example.examplemod.Commands.UtilsCo.Command;
import com.example.examplemod.Module.MOVEMENT.LookAtBlock;
import com.example.examplemod.Module.ModSettings; // Добавьте импорт для класса ModSettings

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class LookAtCommand extends Command {

    private static final Map<Integer, LookPos> savedPositions = new HashMap<>();
    private static final File settingsFile = new File("mod_settings.json"); // Файл для сохранения настроек

    public LookAtCommand() {
        super("lookat");
    }

    @Override
    public String usage() {
        return TextFormatting.RED + "\u00A7l" + "Usage: .lookat X Y Z | .lookat add <posNumber> X Y Z | .lookat pos <posNumber> | .lookat clear <posNumber>";
    }

    @Override
    public String execute(String[] args) {
        if (args.length < 2) {
            return usage();
        }

        String subCommand = args[1];

        if ("add".equalsIgnoreCase(subCommand) && args.length == 6) {
            try {
                int posNumber = Integer.parseInt(args[2]);
                int x = Integer.parseInt(args[3]);
                int y = Integer.parseInt(args[4]);
                int z = Integer.parseInt(args[5]);

                savedPositions.put(posNumber, new LookPos(x, y, z));

                // Сохранение позиции в настройках
                ModSettings settings = ModSettings.loadSettings(settingsFile);
                settings.savedPositions.put(posNumber, new ModSettings.LookPos(x, y, z));
                ModSettings.saveSettings(settings, settingsFile);

                return TextFormatting.GREEN + "\u00A7l" + "Position " + posNumber + " added: X=" + x + ", Y=" + y + ", Z=" + z;
            } catch (NumberFormatException e) { // \u00A7
                return usage();
            }
        } else if ("pos".equalsIgnoreCase(subCommand) && args.length == 3) {
            try {
                int posNumber = Integer.parseInt(args[2]);

                ModSettings settings = ModSettings.loadSettings(settingsFile);
                ModSettings.LookPos lookPos = settings.savedPositions.get(posNumber);

                if (lookPos != null) {
                    LookAtBlock.setBlockToLook(lookPos.x, lookPos.y, lookPos.z);
                    return TextFormatting.GREEN + "\u00A7l" + "Looking at position " + posNumber + ": X=" + lookPos.x + ", Y=" + lookPos.y + ", Z=" + lookPos.z;
                } else {
                    return TextFormatting.RED + "\u00A7l" + "Position " + posNumber + " not found.";
                }
            } catch (NumberFormatException e) {
                return usage();
            }
        } else if ("clear".equalsIgnoreCase(subCommand) && args.length == 3) {
            try {
                int posNumber = Integer.parseInt(args[2]);
                LookPos removedPos = savedPositions.remove(posNumber);

                ModSettings settings = ModSettings.loadSettings(settingsFile);
                settings.savedPositions.remove(posNumber);
                ModSettings.saveSettings(settings, settingsFile);

                if (removedPos != null) {
                    return TextFormatting.GREEN + "\u00A7l" + "Cleared position " + posNumber + ": X=" + removedPos.x + ", Y=" + removedPos.y + ", Z=" + removedPos.z;
                } else {
                    return TextFormatting.RED + "\u00A7l" + "Position " + posNumber + " not found.";
                }
            } catch (NumberFormatException e) {
                return usage();
            }
        } else if (args.length == 4) {
            try {
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                int z = Integer.parseInt(args[3]);

                LookAtBlock.setBlockToLook(x, y, z);

                return TextFormatting.GREEN + "\u00A7l" + "Looking block at X: " + x + ", Y: " + y + ", Z: " + z;
            } catch (NumberFormatException e) {
                return usage();
            }
        } else {
            return usage();
        }
    }

    private static class LookPos {
        int x;
        int y;
        int z;

        LookPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
