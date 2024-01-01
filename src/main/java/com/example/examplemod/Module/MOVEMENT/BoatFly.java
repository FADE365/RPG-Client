package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityBat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class BoatFly extends Module {
    public BoatFly() {
        super("BoatFly", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (mc.player != null && mc.player.getRidingEntity() instanceof EntityBoat) {
            EntityBoat boat = (EntityBoat) mc.player.getRidingEntity();
            boat.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 1 : 0;
        }
    }

}
