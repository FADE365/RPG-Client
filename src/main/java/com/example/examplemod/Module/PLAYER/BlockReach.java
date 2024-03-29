package com.example.examplemod.Module.PLAYER;

import com.example.examplemod.Module.Module;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

public class BlockReach extends Module {
    public BlockReach() {
        super("BlockReach", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            EntityPlayer player = mc.player;
            IAttributeInstance setBlockReachDi = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE);
            player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(player.getUniqueID(), "custom_reach", 0.8f, 1));

        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null && mc.world != null) {
            mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(mc.player.getUniqueID());
        }
    }
}
