package com.example.examplemod.Module.MOVEMENT;

import com.example.examplemod.Module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import org.lwjgl.input.Keyboard;

public class ViewLock extends Module {
    private boolean isEnabled = false;
    private int tickCounter = 0; // Счетчик тиков

    public ViewLock() {
        super("ViewLock", Keyboard.KEY_NONE, Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        isEnabled = true;
        tickCounter = 0; // Сбрасываем счетчик тиков при включении
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        isEnabled = false;
        tickCounter = 0; // Сбрасываем счетчик тиков при выключении
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (isEnabled && event.phase == Phase.START) {
            tickCounter++;

            // Вызываем fixView только на каждом втором тике
            if (tickCounter % 1 == 0) {
                fixView();
            }
        }
    }

    private void fixView() {
        if (mc.player == null) {
            return;
        }

        double playerYaw = mc.player.rotationYaw;
        double playerPitch = mc.player.rotationPitch;

        double[] targetYawArray = {0, 45, 90, 135, 180, -135, -90, -45};
        double[] targetPitchArray = {90, 45, 0, -45, -90};

        double closestYaw = getClosestValue(playerYaw, targetYawArray);
        double closestPitch = getClosestValue(playerPitch, targetPitchArray);

        mc.player.rotationYaw = (float) closestYaw;
        mc.player.rotationPitch = (float) closestPitch;
    }

    private double getClosestValue(double value, double[] array) {
        double closest = array[0];
        double minDiff = Math.min(Math.abs(array[0] - value), Math.abs(array[0] - (value - 360)));

        for (double v : array) {
            double diff = Math.min(Math.abs(v - value), Math.abs(v - (value - 360)));
            if (diff < minDiff) {
                closest = v;
                minDiff = diff;
            }
        }

        if (closest < 0) {
            closest += 360; // Приводим значение к положительному диапазону
        }

        return closest;
    }
}
