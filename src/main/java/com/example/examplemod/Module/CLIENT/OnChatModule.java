package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.ChatUtils;
import org.lwjgl.input.Keyboard;

public class OnChatModule extends Module {

    public static boolean OnChat;

    public OnChatModule() {
        super("OnChatModule", Keyboard.KEY_NONE, Category.CLIENT);
    }

    @Override
    public void onEnable() {
        OnChat = true;
    }

    @Override
    public void onDisable() {
        OnChat = false;
        ChatUtils.sendMessage(this.name + " \u00A7cDisable!");
    }

}
