package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.Module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import javax.swing.text.html.parser.Entity;

public class AntiBot extends Module {
    public AntiBot() {
        super("AntiBot", Keyboard.KEY_NONE, Category.COMBAT);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer.isInvisible() && entityPlayer != mc.player) {
                mc.world.removeEntity(entityPlayer);
            }
        }
    }
}
