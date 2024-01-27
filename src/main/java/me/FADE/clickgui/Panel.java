package me.FADE.clickgui;

import com.example.examplemod.Client;
import com.example.examplemod.Module.CLIENT.PanelBackGround;
import com.example.examplemod.Module.Module;
import font.FontUtils;
import me.FADE.clickgui.utilsSL.PanelState;
import me.FADE.clickgui.utilsSL.PanelStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.examplemod.Module.UI.ui.rainbow;

public class Panel {
    public Minecraft mc = Minecraft.getMinecraft();

    public int x, y, width, height, dragY, dragX;
    public boolean extended, dragging;
    public Module.Category category;
    public PanelState state;
    public boolean isExtended;
    private float animationProgress = 0.0f; // Начальное значение анимации
    private float animationSpeed = 0.05f; // Скорость анимации

    public PanelBackGround PanelGround;

    public List<Button> buttons = new ArrayList<>();

    public Panel(int x, int y, int width, int height, Module.Category category, boolean isExtended) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;

        int y1 = y + height;

        for (Module module : Client.modules) {
            if (module.category == category) {
                buttons.add(new Button(x, y1, width, height, module));
                y1 += height;
            }
        }
        // Вызов метода сортировки
        sortButtonsByModuleNameLength();
        // Загрузка состояния
        loadState();
    }

    // Метод для сортировки кнопок
    private void sortButtonsByModuleNameLength() {
        Collections.sort(buttons, (b1, b2) ->
                Integer.compare(b2.module.getName().length(), b1.module.getName().length()));
    }

    public void setState(PanelState state) {
        if (state != null) {
            this.x = state.x;
            this.y = state.y;
            this.extended = state.isExtended;
        }
    }

    public void saveState() {
        String stateFilePath = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/RPG Client/panel_states.json";
        List<PanelState> states = PanelStateManager.loadPanelStates(stateFilePath);
        if (states == null) {
            states = new ArrayList<>();
        }
        PanelState currentState = new PanelState(x, y, extended, this.category.name());

        // Удаление старого состояния и добавление нового
        states.removeIf(state -> state.category.equals(this.category.name()));
        states.add(currentState);

        PanelStateManager.savePanelStates(states, stateFilePath);
    }



    public void loadState() {
        String stateFilePath = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/RPG Client/panel_states.json";
        List<PanelState> states = PanelStateManager.loadPanelStates(stateFilePath);
        if (states != null) {
            for (PanelState state : states) {
                if (state != null && this.category.name().equals(state.category)) {
                    this.x = state.x;
                    this.y = state.y;
                    this.extended = state.isExtended;
                    break;
                }
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        loadState();
        setState(state);
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        // Вычисляем текущую высоту панели на основе анимации
        int currentHeight = (int) (extended ? (height + height * animationProgress) : (height - height * animationProgress));

        // Отрисовка обводки панели
        int borderColor = rainbow(5); // Цвет обводки (черный)
        Gui.drawRect(x, y, x + width, y + 1, borderColor); // Верхняя граница
        Gui.drawRect(x, y + currentHeight - 1, x + width, y + currentHeight, borderColor); // Нижняя граница
        Gui.drawRect(x, y, x + 1, y + currentHeight, borderColor); // Левая граница
        Gui.drawRect(x + width - 1, y, x + width, y + currentHeight, borderColor); // Правая граница

        // Отрисовка фона и текста панели
        Gui.drawRect(x + 1, y + 1, x + width - 1, y + currentHeight - 1, PanelGround.getThisColor().hashCode());
        //mc.fontRenderer.drawStringWithShadow(category.name(), x + width / 2 - mc.fontRenderer.getStringWidth(category.name()) / 2, y + currentHeight / 2 - 9 / 2, -1);
        FontUtils.normal.drawString(category.name(),
                x + width / 2 - FontUtils.normal.getStringWidth(category.name()) / 2,
                y + currentHeight / 2 - 2 / 2,
                PanelGround.getWPColor().hashCode());



        if (extended) {
            int y1 = y + currentHeight;
            for (Button button : buttons) {
                button.x = x;
                button.y = y1;
                button.drawScreen(mouseX, mouseY, partialTicks);
                y1 += height; // расстояние между панелями!
            }
        }

        // Анимация открытия и закрытия панели
        if (extended && animationProgress < 0.35f) {
            animationProgress = Math.min(1.0f, animationProgress + animationSpeed);
        } else if (!extended && animationProgress > 0.0f) {
            animationProgress = Math.max(0.0f, animationProgress - animationSpeed);
        }
    }



    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (extended) {
            for (Button button : buttons) {
                button.keyTyped(typedChar, keyCode);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (HoverUtils.hovered(mouseX, mouseY, x, y, x + width, y + height)) {
            if (mouseButton == 0) {
                dragX = mouseX - x;
                dragY = mouseY - y;
                dragging = true;

            }
            else if (mouseButton == 1)
            {
                // Расширяем или сворачиваем панель по нажатию правой кнопки мыши
                extended = !extended;
                saveState();
            }
        }

        if (extended) {
            for (Button button : buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;

        if (extended) {
            for (Button button : buttons) {
                button.mouseReleased(mouseX, mouseY, state);
            }
        }
        // Сохраняем состояние после окончания перетаскивания
        if (!dragging) {
            saveState();
        }
    }
}
