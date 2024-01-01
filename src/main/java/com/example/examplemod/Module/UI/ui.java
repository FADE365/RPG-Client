package com.example.examplemod.Module.UI;

import com.example.examplemod.Client;
import com.example.examplemod.Module.CLIENT.Panic;
import com.example.examplemod.Module.Module;
import font.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static net.minecraft.client.gui.Gui.drawRect;

public class ui {
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY); // Получение часов
        int minutes = calendar.get(Calendar.MINUTE); // Получение минут
        switch (e.getType()) {
            case TEXT:
                if (!Panic.isPanic) {
                    int y = 10;
                    final int[] counter = {1};

                    Minecraft mc = Minecraft.getMinecraft();
                    FontRenderer fr = mc.fontRenderer;
                    ScaledResolution sr = new ScaledResolution(mc);

                    int posY = 10;

                    try {
                        String text = Client.cName + "§f | " + mc.getSession().getUsername() + " | " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP +
                                " | FPS: §a" + Minecraft.getDebugFPS() + "§f | Ping: §a" + mc.getCurrentServerData().pingToServer;

                        drawRect(5, 5, FontUtils.normal.getStringWidth(text) > 190 ? (int) (FontUtils.normal.getStringWidth(text) + 14) : 250, 18, new Color(0xBB151515, true).hashCode());
                        drawRect(5, 5, FontUtils.normal.getStringWidth(text) > 190 ? (int) (FontUtils.normal.getStringWidth(text) + 14) : 200, 4, rainbow(300));

                        FontUtils.normal.drawString(text, 10, 10, -1);
                    } catch (Exception ex) {
                        drawRect(5, 5, 155, 18, new Color(0x151515).hashCode());
                        drawRect(5, 5, 155, 4, rainbow(300));

                        FontUtils.normal.drawString(Client.cName + "§f | " + mc.getSession().getUsername() +
                                " | FPS: §a" + Minecraft.getDebugFPS() + ("§f|§4" + hours + "§f:§4" + minutes), 10, 10, rainbow(150));
                    }

                /*
                fr.drawString("Tutorial§aClient §fB§a1.0", 5, 5, -1);
                fr.drawString("§fFPS: §a" + Minecraft.getDebugFPS(), 5, 15, -1);

                 */

                    ArrayList<Module> enabledMods = new ArrayList<>();

                    for (Module module : Client.modules) {
                        if (module.toggled) {
                            enabledMods.add(module);
                        }
                    }

                    enabledMods.sort((module1, module2) -> mc.fontRenderer.getStringWidth(module2.getName()) - mc.fontRenderer.getStringWidth(module1.getName()));

                    for (Module module : enabledMods) {
                        Gui.drawRect(sr.getScaledWidth(), y, sr.getScaledWidth() - 2,
                                y + 10, rainbow(counter[0] * 300));

                        fr.drawStringWithShadow(module.name, sr.getScaledWidth() - 4 - fr.getStringWidth(module.name),
                                y, rainbow(counter[0] * 300));
                        y += 10;
                        counter[0]++;
                    }

                } else {
                    Minecraft.getMinecraft().fontRenderer.drawString("FPS: " + Minecraft.getDebugFPS(), 5, 5, -1);
                }
            default:
                break;
        }
    }



    public static int rainbow(int delay) {
        double rainbawState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbawState %= 360;
        return Color.getHSBColor((float) (rainbawState / 360.0f), 0.5f, 1f).getRGB();
    }
}
