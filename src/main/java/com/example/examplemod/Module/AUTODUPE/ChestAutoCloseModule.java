package com.example.examplemod.Module.AUTODUPE;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ChestAutoCloseModule extends Module {
    private long chestOpenTimestamp = 0;

    public ChestAutoCloseModule() {
        super("ChestAutoClose", Keyboard.KEY_NONE, Category.PLAYER);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player != null && this.isEnabled()) { // Проверка на активность модуля
            if (player.openContainer instanceof ContainerChest) {
                // Если сундук открыт, начинаем или обновляем таймер
                if (chestOpenTimestamp == 0) {
                    chestOpenTimestamp = System.currentTimeMillis();
                } else {
                    long timeElapsed = System.currentTimeMillis() - chestOpenTimestamp;
                    if (timeElapsed > 30000) { // 30 секунд в миллисекундах
                        player.closeScreen(); // Закрытие инвентаря сундука
                        chestOpenTimestamp = 0; // Сброс таймера
                    }
                }
            } else {
                chestOpenTimestamp = 0; // Сундук закрыт, сброс таймера
            }
        }
    }
}
