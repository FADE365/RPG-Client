package me.FADE.clickgui.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Slider {
    private float value;
    private float minValue;
    private float maxValue;
    private String label;
    private int width = 150; // Ширина слайдера
    private int height = 20; // Высота слайдера
    private boolean dragging; // Состояние перетаскивания

    public Slider(String label, float minValue, float maxValue, float initialValue) {
        this.label = label;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = initialValue;
    }

    public void render(int x, int y) {
        // Отрисовка слайдера
        // Рассчитываем положение ползунка на основе текущего значения
        int sliderPosition = (int) (x + (value - minValue) / (maxValue - minValue) * (width - 8)); // 8 - ширина ползунка

        // Рисуем фон слайдера
        Gui.drawRect(x, y, x + width, y + height, 0xFF000000); // Чёрный фон

        // Рисуем ползунок
        Gui.drawRect(sliderPosition, y, sliderPosition + 8, y + height, 0x8ccb5e);

        // Рисуем текст слайдера
        String displayValue = String.format("%s: %.2f", label, value);
        int strWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(displayValue);
        Minecraft.getMinecraft().fontRenderer.drawString(displayValue, x + (width / 2) - (strWidth / 2), y + (height - 8) / 2, 0xFFFFFFFF); // Белый текст
    }

    public void onDrag(int mouseX, int x, int y) {
        // Обработка перемещения ползунка слайдера
        if (this.dragging) {
            // Преобразуем положение мыши в значение слайдера
            float newValue = (mouseX - x) / (float) width * (maxValue - minValue) + minValue;
            setValue(newValue);
        }
    }

    public void onMouseClick(int mouseX, int mouseY, int mouseButton, int x, int y) {
        if (mouseButton == 0 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            this.dragging = true;
            onDrag(mouseX, x, y);
        }
    }

    public void onMouseRelease(int mouseButton) {
        if (mouseButton == 0) {
            this.dragging = false;
        }
    }

    public void setValue(float value) {
        // Устанавливаем значение, убеждаясь, что оно находится в допустимом диапазоне
        this.value = Math.max(minValue, Math.min(maxValue, value));
    }

    public float getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
