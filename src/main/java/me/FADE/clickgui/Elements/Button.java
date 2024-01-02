package me.FADE.clickgui.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button extends GuiButton {
    private IButtonClickAction action;

    public Button(int buttonId, int x, int y, int width, int height, String buttonText, IButtonClickAction action) {
        super(buttonId, x, y, width, height, buttonText);
        this.action = action; // Сохраняем действие для кнопки
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            super.drawButton(mc, mouseX, mouseY, partialTicks); // Рисуем кнопку
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) { // Проверка, была ли нажата кнопка
            this.action.onClick(); // Выполнение действия
            return true;
        }
        return false;
    }

    public interface IButtonClickAction {
        void onClick(); // Метод, который будет выполняться при клике
    }
}
