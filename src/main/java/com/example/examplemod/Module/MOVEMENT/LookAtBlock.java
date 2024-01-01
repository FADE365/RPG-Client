package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.ChatUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import org.lwjgl.input.Keyboard;

public class LookAtBlock extends Module {
    public static int blockX;
    public static int blockY;
    public static int blockZ;
    private boolean isEnabled = false;
    private float originalYaw = 0.0f;

    public LookAtBlock() {
        super("LookAtBlock", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            isEnabled = true;
            // blockX = (int) mc.player.posX;
            // blockY = (int) mc.player.posY;
            // blockZ = (int) mc.player.posZ;
            originalYaw = mc.player.rotationYaw;
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        isEnabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (isEnabled) {
            e.getPhase();
            lookAtBlock();
        }
    }

    public static void setBlockToLook(int x, int y, int z) {
        blockX = x;
        blockY = y;
        blockZ = z;
        ChatUtils.sendMessage("Yes set");
    }

    private void lookAtBlock() {
        double dX = blockX + 0.5 - mc.player.posX;
        double dY = blockY + 0.5 - (mc.player.posY + mc.player.getEyeHeight());
        double dZ = blockZ + 0.5 - mc.player.posZ;
        double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);

        float yaw = (float) Math.toDegrees(Math.atan2(dZ, dX)) - 90;

        mc.player.rotationYaw = yaw;
        mc.player.rotationYawHead = yaw;
    }
}
