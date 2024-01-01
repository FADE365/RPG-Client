package com.example.examplemod.Module.PLAYER;

import com.example.examplemod.Module.Module;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", Keyboard.KEY_NONE, Category.PLAYER); // Замените на нужные значения
    }

    @SubscribeEvent
    public void onLivingUpdate(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END && e.side == Side.CLIENT) {
            if (!isEnabled()) {
                return;
            }

            ItemStack heldItemOffhand = e.player.getHeldItemOffhand();
            ItemStack totem = findTotemInInventory();

            if (totem != null) {
                if (heldItemOffhand == ItemStack.EMPTY) {
                    e.player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, totem);
                }
            }
        }
    }



    private ItemStack findTotemInInventory() {
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                return stack;
            }
        }
        return null;
    }
}
