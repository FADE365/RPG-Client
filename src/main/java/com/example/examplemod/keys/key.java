
package com.example.examplemod.keys;

import com.example.examplemod.Client;
import com.example.examplemod.Module.CLIENT.Hud;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class key {

    private static int hudKey = Hud.bindKey;; // Клавиша HUD по умолчанию

    public static void setHudKey(int keyCode) {
        Hud.setBindKey(keyCode);
        hudKey = keyCode;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                Client.KeyPress(Keyboard.getEventKey());
                // Проверяем, если клавиша бинда Hud была нажата, открываем ClickGui
//                if (Keyboard.getEventKey() == hudKey && !Panic.isPanic) {
//                    Minecraft.getMinecraft().displayGuiScreen(Client.clickGui);
//                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
    //    System.out.println("hudkey -> " + hudKey);
    }
}