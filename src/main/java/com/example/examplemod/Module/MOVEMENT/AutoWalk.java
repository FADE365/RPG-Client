/*
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
        startWalking();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        stopWalking();
        super.onDisable();
    }

    private void startWalking() {
        // Получаем текущие координаты игрока
        int playerX = (int) mc.player.posX;
        int playerZ = (int) mc.player.posZ;

        // Задаем цель для Baritone на 100 блоков впереди от текущего положения игрока
        GoalXZ goal = new GoalXZ(playerX + 100, playerZ);

        // Передаем цель Baritone и активируем его
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(goal);
    }

    private void stopWalking() {
        // Останавливаем Baritone
        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
    }

}
*/