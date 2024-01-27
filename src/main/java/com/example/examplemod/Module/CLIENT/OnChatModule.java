package com.example.examplemod.Module.CLIENT;

import com.example.examplemod.Module.Module;
import com.example.examplemod.Utils.ChatUtils;
import org.lwjgl.input.Keyboard;

public class OnChatModule extends Module {

    public static boolean OnChat = true;

    public OnChatModule() {
        super("Send Toggle", Keyboard.KEY_NONE, Category.CLIENT);
        this.setToggled(true);
    }

    @Override
    public void onEnable() {
        OnChat = true;
    }

    public static void SendChat(String MName,boolean state) {
        if (OnChat) {
            if (state) {
                ChatUtils.sendMessage(MName + " \u00A79Enable!");
            } else {
                ChatUtils.sendMessage(MName + " \u00A79Disable!");
            }
        }
    }

    @Override
    public void onDisable() {
        OnChat = false;
        ChatUtils.sendMessage(this.name + " \u00A7cDisable!");
    }

    public void setOnChat(boolean state) {
        OnChat = state;
    }

    public boolean getOnChat() {
        return OnChat;
    }

}
