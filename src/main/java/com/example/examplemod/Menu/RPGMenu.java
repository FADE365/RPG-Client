package com.example.examplemod.Menu;

import com.example.examplemod.Menu.Tools.ChangeUser;
import com.example.examplemod.Module.CLIENT.WaterMark;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.examplemod.Module.UI.ui.rainbow;

public class RPGMenu extends GuiScreen {
    private RotatingCube rotatingCube;
    private static final ResourceLocation texture = new ResourceLocation("byfade.png");
    private MatrixCodeBackground matrixCodeBackground;
    public static WaterMark waterMark;
    private static final ResourceLocation MUSIC_LOCATION = new ResourceLocation("your_mod_id:music/menu_music.ogg"); // Замените на путь к вашей музыке

    public RPGMenu() {
        super();
        rotatingCube = new RotatingCube();
    }

    private int buttonMargin = 2;
    private int borderColor = 0xFF00FF00; // Зелёный
    private int backColor = 0x00000000; // Чёрный
    private int textColor = 0xFFFFFFFF; // Белый

    public void initGui() {
        super.initGui();
        int i = this.height / 4 + 48;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, i + 72 + 12, 98,
                20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 2, i + 72 + 12, 98,
                20, I18n.format("Quit")));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, i + 72 - 12, 98,
                20, I18n.format("Change Name")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, i + 72 - 12, 98,
                20, I18n.format("Author")));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, i + 72 - 34, 200,
                20, I18n.format("Multiplayer")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, i + 72 - 56, 200,
                20, I18n.format("SinglePlayer")));

        matrixCodeBackground = new MatrixCodeBackground(fontRenderer, width, height);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
        }
        if (button.id == 1) {
            mc.shutdown();
        }
        if (button.id == 2) {
            mc.displayGuiScreen(new ChangeUser());
        }
        if (button.id == 3) {
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://discord.gg/7EArhRS76s"));
                }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
            }
        }
        if (button.id == 4) {
            mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 5) {
            mc.displayGuiScreen(new GuiWorldSelection(this));
        }

        super.actionPerformed(button);
    }

    @Override
    public void updateScreen() {
        rotatingCube.update(); // Обновляем состояние кубика
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.color(1, 1, 1, 1);
        mc.renderEngine.bindTexture(texture);

        Gui.drawScaledCustomSizeModalRect(0, 0,0,0, this.width, this.height, this.width, this.height, this.width, this.height);
        DrawLogo.DrawString(5,
                "RPG-Client",
                this.width / 10 - this.fontRenderer.getStringWidth("RPG-Client") / 2,
                this.height / 20,
                rainbow(50));

        DrawLogo.DrawString(
                3,
                "The truth is out there",
                this.width / 6 - this.fontRenderer.getStringWidth("The truth is out there") / 2,
                this.height / 14,
                rainbow(50));


        rotatingCube.render(); // Рисуем кубик
        matrixCodeBackground.update();
        matrixCodeBackground.render();

        // Отрисовка кастомных кнопок
        for (GuiButton button : this.buttonList) {
            drawCustomButton(button.x, button.y, button.width, button.height, button.displayString);
        }

    }

    private void drawCustomButton(int x, int y, int width, int height, String text) {
        // Отрисовка фона кнопки
        drawRect(x + buttonMargin, y + buttonMargin, x + width - buttonMargin, y + height - buttonMargin, backColor);
        // Отрисовка границы
        drawRect(x, y, x + width, y + buttonMargin, borderColor); // верх
        drawRect(x, y + height - buttonMargin, x + width, y + height, borderColor); // низ
        drawRect(x, y, x + buttonMargin, y + height, borderColor); // лево
        drawRect(x + width - buttonMargin, y, x + width, y + height, borderColor); // право
        // Отрисовка текста
        drawCenteredString(fontRenderer, text, x + width / 2, y + (height - 8) / 2, textColor);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiButton button : this.buttonList) {
            if (button.mousePressed(mc, mouseX, mouseY)) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (GuiButton button : this.buttonList) {
            button.mouseReleased(mouseX, mouseY);
        }
    }

}