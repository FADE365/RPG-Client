package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Speed extends Module {
    public Speed() {
        super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START && e.player == mc.player) {
            if (mc.player.onGround && mc.player.moveForward > 0 && !mc.player.isInWater() && !mc.player.isInLava()) {
                double speed = 0.5;
                if (!mc.player.isSprinting()) {
                    mc.player.setSprinting(true);
                }
                mc.player.motionY = 0.0;

                float yaw = mc.player.rotationYaw * 0.0174532920F;
                mc.player.motionY -= MathHelper.sin(yaw) * (speed / 5);
                mc.player.motionZ += MathHelper.cos(yaw) * (speed / 5);
            } else {
                if (mc.player.isSprinting()) {
                    mc.player.setSprinting(false);
                }
            }
        }
    }

}
