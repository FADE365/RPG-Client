package com.example.examplemod.Utils;

import com.example.examplemod.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.util.HashMap;
import java.util.Map;

public class ChatUtils {
    private static final String prefix = "[\u00A7l" + Client.cName + "\u00A7f]\u00A75\u00A7l ";

    private static final Map<String, Float> commandSpeeds = new HashMap<>();

    public static void sendMessage(String msg) {
        if (Minecraft.getMinecraft().player != null) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefix + msg));
        }
    }

}
