package com.example.examplemod.Module.COMBAT;

import com.example.examplemod.Module.Module;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;


public class AutoDisconnect extends Module {
    public AutoDisconnect() {
        super("AutoDisconnect", Keyboard.KEY_NONE, Category.COMBAT);

    }
    private Boolean Enable = false;

    @Override
    public void onEnable() {
        Enable = true;
    }

    @Override
    public void onDisable() {
        Enable = false;
    }

    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (Enable && mc.player != null && mc.player.getHealth() <= 1) {
            disconnectFromServer(); // вызываем метод отключения от сервера
        }
    }

    private void disconnectFromServer() {
        mc.world.sendQuittingDisconnectingPacket();
        mc.loadWorld(null);
        mc.displayGuiScreen(new GuiMainMenu());
    }
}
