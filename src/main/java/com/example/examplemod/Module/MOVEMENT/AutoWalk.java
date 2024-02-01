package com.example.examplemod.Module.MOVEMENT;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalXZ;
import com.example.examplemod.Module.Module;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("Auto Walk", 0,Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.world != null) {
            startWalking();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.world != null) {
            stopWalking();
        }
        super.onDisable();
    }

    private void startWalking() {
        try {
            // Получаем угол поворота игрока
            float yaw = mc.player.rotationYaw;

            // Округляем угол до ближайшего значения (0, 45, 90, 135, 180, 225, 270, 315, 360)
            int roundedYaw = Math.round(yaw / 45) * 45;
            if (roundedYaw >= 360) {
                roundedYaw = 0;
            }

            // Рассчитываем цель в зависимости от угла
            int dx = (int) (Math.cos(Math.toRadians(roundedYaw)) * 10000);
            int dz = (int) (Math.sin(Math.toRadians(roundedYaw)) * 10000);

            int playerX = (int) mc.player.posX;
            int playerZ = (int) mc.player.posZ;

            // Задаем цель для Baritone
            GoalXZ goal = new GoalXZ(playerX + dx, playerZ + dz);
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(goal);

        } catch (NoClassDefFoundError e) {
            System.out.println("Baritone not found");
            setToggled(false);
        }
    }


    private void stopWalking() {
        try {
            // Останавливаем Baritone
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        } catch (NoClassDefFoundError e) {
            System.out.println("Baritone not found");
        }
    }

}
