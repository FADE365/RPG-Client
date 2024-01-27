package me.FADE.clickgui;

import me.FADE.clickgui.Elements.Button;
import me.FADE.clickgui.Elements.Slider;
import me.FADE.clickgui.Elements.Toggle;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.examplemod.Module.UI.ui.rainbow;

public class SettingPanel {
    private int x, y, width, height;
    private boolean isVisible;
    private boolean isDragging;
    private int dragX, dragY;
    private static List<SettingPanel> allPanels = new ArrayList<>();
    private List<Button> buttons;
    private int currentY;

    public Slider slider;

    private ArrayList<Toggle> toggles = new ArrayList<>();
    private ArrayList<Slider> sliders = new ArrayList<>();

    // Методы для добавления переключателей и слайдеров
    public void addToggle(Toggle toggle) {
        toggles.add(toggle);
    }

    public void addSlider(Slider slider) {
        sliders.add(slider);
    }

    public SettingPanel(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 50;
        this.isVisible = false;
        allPanels.add(this); // Добавляем эту панель в список всех панелей
        this.buttons = new ArrayList<>();
    }

    // Метод для добавления кнопки
    public void addButton(Button button) {
        buttons.add(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (isVisible) {
            // Отрисовка панели
            int borderColor = rainbow(15); // Цвет обводки (белый)
            Gui.drawRect(x, y, x + width, y + 1, borderColor); // Верхняя граница
            Gui.drawRect(x, y + height - 1, x + width, y + height, borderColor); // Нижняя граница
            Gui.drawRect(x, y, x + 1, y + height, borderColor); // Левая граница
            Gui.drawRect(x + width - 1, y, x + width, y + height, borderColor); // Правая граница

            // Отрисовка фона и текста панели
            Gui.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, new Color(0xA9000000, true).hashCode());
            // Рисуйте содержимое панели (например, настройки) здесь

            if (isDragging) {
                // Обновляем позицию панели во время перетаскивания
                x = mouseX - dragX;
                y = mouseY - dragY;
            }
            // Отрисовка слайдеров


            for (Slider slider : sliders) {
                slider.render(x, currentY);
                currentY += 25; // или любое другое смещение для следующего элемента
            }
            // Отрисовка переключателей и слайдеров
            for (Toggle toggle : toggles) {
                toggle.render(x, y);
                y += 20; // или любое другое смещение для следующего элемента
            }


        }
    }

    public void toggleVisibility() {
        isVisible = !isVisible;

        if (isVisible) {
            // Скрываем все остальные панели
            for (SettingPanel panel : allPanels) {
                if (panel != this) {
                    panel.isVisible = false;
                }
            }
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return isVisible && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isMouseOver(mouseX, mouseY)) {
            isDragging = true;
            dragX = mouseX - x;
            dragY = mouseY - y;
        } else {
            for (Slider slider : sliders) {
                slider.render(x, y);
                y += 20; // или любое другое смещение для следующего элемента
            }
            for (Slider slider : sliders) {
                slider.onMouseClick(mouseX, mouseY, mouseButton, x, y);
            }
        }
    }

    public void onMouseRelease(int mouseX, int mouseY, int state) {
        isDragging = false;
        for (Slider slider : sliders) {
            slider.onMouseRelease(state);
        }
    }

    public void onMouseDrag(int mouseX, int mouseY) {
        // Перемещение панели с мышью
        isDragging = false;
    }
}