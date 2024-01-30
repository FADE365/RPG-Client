package com.example.examplemod.Module.AUTODUPE;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import com.example.examplemod.Module.Module;

import java.lang.reflect.Field;

public class ChestStealer extends Module {
    private Minecraft mc = Minecraft.getMinecraft();
    private Field horseChestField;

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_NONE, Category.AUTODUPE);
        // Попытка получить доступ к полю horseChest через отражение
        try {
            horseChestField = AbstractChestHorse.class.getDeclaredField("horseChest");
            horseChestField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.getRidingEntity() instanceof AbstractChestHorse) {
            AbstractChestHorse horse = (AbstractChestHorse) mc.player.getRidingEntity();
            try {
                ContainerHorseChest horseChest = (ContainerHorseChest) horseChestField.get(horse);
                lootDonkey(horseChest);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void fillChest() {
        // Код для перемещения предметов из инвентаря игрока в сундук
        for (int i = 54; i <= 89; i++) {
            if (mc.player.openContainer.getSlot(i).getHasStack()) {
                mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
            }
        }
    }
    public void lootDonkey(ContainerHorseChest horseInventory) {
        for (int i = 0; i < horseInventory.getSizeInventory(); i++) {
            if (!horseInventory.getStackInSlot(i).isEmpty()) {
                mc.playerController.windowClick(mc.player.openContainer.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
            }
        }
    }
}