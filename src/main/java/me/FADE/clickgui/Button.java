package me.FADE.clickgui;

import com.example.examplemod.Module.CLIENT.Hud;
import com.example.examplemod.Module.ModSettings;
import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Button {
    public Minecraft mc = Minecraft.getMinecraft();

    public int x, y, width, height;
    public boolean binding;
    public Module module;
    private SettingPanel settingPanel;

    public Button(int x, int y, int width, int height, Module module) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.module = module;
        this.settingPanel = new SettingPanel(x + width + 2, y); // Разместите панель рядом с кнопкой
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Отрисовка обводки
        int borderColor = new Color(0xCB000000, true).hashCode(); // Цвет обводки (белый)
        Gui.drawRect(x, y, x + width, y + 1, borderColor); // Верхняя граница
        Gui.drawRect(x, y + height - 1, x + width, y + height, borderColor); // Нижняя граница
        Gui.drawRect(x, y, x + 1, y + height, borderColor); // Левая граница
        Gui.drawRect(x + width - 1, y, x + width, y + height, borderColor); // Правая граница

        // Отрисовка фона и текста кнопки
        Gui.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, new Color(0xA9000000, true).hashCode());
        mc.fontRenderer.drawStringWithShadow(!binding ? module.name + (module.keyCode != Keyboard.KEY_NONE ? " : " + Keyboard.getKeyName(module.keyCode) : "") : "< PRESS KEY >", x + width / 2 - mc.fontRenderer.getStringWidth(!binding ? module.name +  (module.keyCode != Keyboard.KEY_NONE ? " : " + Keyboard.getKeyName(module.keyCode) : "") : "< PRESS KEY >") / 2, y + height / 2 - 9 / 2, module.toggled && !binding ? new Color(0x36D003).hashCode() : -1);

        //String buttonText = !binding ? module.name + (module.keyCode != Keyboard.KEY_NONE ? " : " + Keyboard.getKeyName(module.keyCode) : "") : "< PRESS KEY >";
        //int color = module.toggled && !binding ? new Color(0x36D003).hashCode() : -1;
        //FontUtils.normal.drawString(buttonText,
        //        x + width / 2 - FontUtils.normal.getStringWidth(buttonText) / 2,
        //        y + height / 2,
        //        color);

        settingPanel.drawScreen(mouseX, mouseY, partialTicks);
    }


    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (binding) {
            module.keyCode = keyCode;
            binding = false;

            if (keyCode == Keyboard.KEY_ESCAPE) {
                module.keyCode = 0;
            }

            // Сохраняем состояния модулей после установки новой клавиши
            ModSettings settings = ModSettings.loadSettings(new File("mod_settings.json"));
            settings.keyBindings.put(module.getName(), module.getKey());
            ModSettings.saveSettings(settings, new File("mod_settings.json"));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (HoverUtils.hovered(mouseX, mouseY, x, y, x + width, y + height)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 2) {
                binding = !binding;
                if (module.getName().equals("Hud") && binding) {
                    Hud hudModule = (Hud) module;
                    hudModule.setBindKey(Keyboard.KEY_NONE);
                }
            } else if (mouseButton == 1) {
                settingPanel.toggleVisibility();
            }
        }

        if (settingPanel.isMouseOver(mouseX, mouseY)) {
            // Обработка кликов внутри панели настройки
            settingPanel.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }



    protected void mouseReleased(int mouseX, int mouseY, int state) {
        settingPanel.onMouseDrag(mouseX, mouseY);
    }

}
