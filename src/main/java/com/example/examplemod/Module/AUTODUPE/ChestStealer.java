package com.example.examplemod.Module.AUTODUPE;

import com.example.examplemod.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class ChestStealer extends Module {
    private final Minecraft mc = Minecraft.getMinecraft();
    private int delayTimer = 0;
    private int chestSlot = -1;
    private Field horseChestField;

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_NONE, Category.AUTODUPE);
        try {
            horseChestField = AbstractChestHorse.class.getDeclaredField("field_190695_dC"); // Отражение поля horseChest
            horseChestField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player != null && mc.world != null && !mc.player.isSneaking()) {
            if (mc.player.isRiding() && mc.player.getRidingEntity() != null) {
                Entity ridingEntity = mc.player.getRidingEntity();

                if (ridingEntity instanceof AbstractChestHorse) {
                    AbstractChestHorse chestHorse = (AbstractChestHorse) ridingEntity;

                    if (horseChestField != null && chestSlot == -1) {
                        try {
                            // Получаем доступ к инвентарю лошади с помощью отражения
                            ItemStack[] horseChest = (ItemStack[]) horseChestField.get(chestHorse);

                            // Находим первый пустой слот
                            for (int i = 0; i < horseChest.length; i++) {
                                if (horseChest[i].isEmpty()) {
                                    chestSlot = i;
                                    break;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    if (chestSlot != -1) {
                        try {
                            ItemStack chestItemStack = ((ItemStack[]) horseChestField.get(chestHorse))[chestSlot];

                            if (!chestItemStack.isEmpty()) {
                                // Перекладываем предмет из инвентаря лошади в инвентарь игрока
                                mc.playerController.windowClick(mc.player.openContainer.windowId, chestSlot, 0, ClickType.QUICK_MOVE, mc.player);
                                delayTimer = 5; // Задержка перед взятием следующего предмета
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (chestSlot != -1) {
                    // Выходим из инвентаря лошади
                    chestSlot = -1;
                }
            }
        }
    }
}
