package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;

import com.example.examplemod.Utils.ChatUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Rotation extends Module {
    private boolean isEnabled = false;

    public Rotation() {
        super("Rotation", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            isEnabled = true;
            rotatePlayerView();
            toggle();
            ChatUtils.sendMessage(this.name + " \u00A72>Toggle<");
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (isEnabled) {
            if (mc.player != null && mc.world != null) {
                rotatePlayerView();
                isEnabled = false;
                super.onDisable();
                // Отключаем функцию после разворота
            }
        }
    }

    private void rotatePlayerView() {
        mc.player.rotationYaw -= 180;
        mc.player.rotationPitch = 0;
    }
}
