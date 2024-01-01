package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.ChatUtils;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Fly extends Module{
    public Fly() {
        super("Fly", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.allowFlying = false;
        }
    }
}

