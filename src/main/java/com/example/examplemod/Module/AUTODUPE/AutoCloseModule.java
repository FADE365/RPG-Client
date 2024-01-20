package com.example.examplemod.Module.AUTODUPE;

import com.example.examplemod.Module.Module;
import me.FADE.clickgui.Elements.Slider;
import me.FADE.clickgui.Elements.Toggle;
import me.FADE.clickgui.SettingPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class AutoCloseModule extends Module {
    private SettingPanel settingPanel;
    private Toggle myToggle;
    private Slider mySlider;

    private long chestOpenTimestamp = 0;
    private long playerStillTimestamp = 0;
    private long startMovingTimestamp = 0;
    private BlockPos lastPosition = null;
    private boolean isMovingRight = true; // Начинаем движение вправо

    KeyBinding LeftKey = mc.gameSettings.keyBindLeft;
    KeyBinding RightKey = mc.gameSettings.keyBindRight;

    public AutoCloseModule() {
        super("AutoClose", Keyboard.KEY_NONE, Category.AUTODUPE);
        settingPanel = new SettingPanel(100, 100);
        myToggle = new Toggle("Тоггл", true);
        mySlider = new Slider("Слайдер", 0.0f, 100.0f, 0.5f);

        // Теперь это вызовы методов находятся в конструкторе
        settingPanel.addToggle(myToggle);
        settingPanel.addSlider(mySlider);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null && this.isEnabled()) {
            // Проверка на то, стоит ли игрок на одном месте
            if (lastPosition == null || !lastPosition.equals(player.getPosition())) {
                lastPosition = player.getPosition();
                playerStillTimestamp = System.currentTimeMillis(); // Обновляем таймер
                startMovingTimestamp = 0;
            } else {
                long timeStill = System.currentTimeMillis() - playerStillTimestamp;
                if (timeStill > 20000 && startMovingTimestamp == 0) { // 20 секунд стоял на месте
                    player.closeScreen(); // Закрытие всех инвентарей
                    startMovingTimestamp = System.currentTimeMillis(); // Начинаем движение
                }
            }

            // Логика движения
            if (startMovingTimestamp != 0) {
                long movingTime = System.currentTimeMillis() - startMovingTimestamp;
                if (movingTime <= 5000) { // Первые 5 секунд - движение вправо
                    KeyBinding.setKeyBindState(RightKey.getKeyCode(), true);
                    KeyBinding.setKeyBindState(LeftKey.getKeyCode(), false); // Убедитесь, что ключ движения влево отключен
                } else if (movingTime <= 10000) { // Следующие 5 секунд - движение влево
                    KeyBinding.setKeyBindState(RightKey.getKeyCode(), false); // Отключаем движение вправо
                    KeyBinding.setKeyBindState(LeftKey.getKeyCode(), true);
                } else {
                    // Останавливаем все движения после 10 секунд
                    KeyBinding.setKeyBindState(RightKey.getKeyCode(), false);
                    KeyBinding.setKeyBindState(LeftKey.getKeyCode(), false);
                    startMovingTimestamp = 0; // Сброс таймера движения
                }
            }


            // Обработка открытия сундука
            handleChestOpen(player);
        }
        if (this.isEnabled()) {
            settingPanel.drawScreen(Mouse.getX(), Mouse.getY(), 0); // Подставьте правильные аргументы
        }
    }

    private void handleChestOpen(EntityPlayerSP player) {
        if (player.openContainer instanceof ContainerChest) {
            if (chestOpenTimestamp == 0) {
                chestOpenTimestamp = System.currentTimeMillis();
            } else {
                long timeElapsed = System.currentTimeMillis() - chestOpenTimestamp;
                if (timeElapsed > 30000) {
                    player.closeScreen();
                    chestOpenTimestamp = 0;
                }
            }
        } else {
            chestOpenTimestamp = 0;
        }
    }

    private void movePlayer(MovementInput movementInput, boolean moveRight) {
        if (moveRight) {
            movementInput.moveStrafe = 1; // Движение вправо
        } else {
            movementInput.moveStrafe = -1; // Движение влево
        }
    }

    private void stopMoving(MovementInput movementInput) {
        movementInput.moveStrafe = 0; // Остановка движения
    }
}
