package com.example.examplemod.Utils;

import com.example.examplemod.Commands.UtilsCo.Command;
import com.example.examplemod.Commands.UtilsCo.Commands;
import com.example.examplemod.Module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Iterator;

@SideOnly(Side.CLIENT)
public class CommandUtils extends Module {
    private static String ESCAPE_CHARACTER = ".";
    private Commands commands = new Commands();

    public CommandUtils() {
        super("Commands", Keyboard.KEY_NONE, Category.CLIENT);
    }

    public Command getCommand(String name) {
        return this.commands.getCommand(name);
    }

    protected void enable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onDisconnect() {
        Iterator var1 = this.commands.getCommands().iterator();

        while(var1.hasNext()) {
            Command c = (Command)var1.next();
            c.onDisconnect();
        }

    }

    public boolean displayInGui() {
        return false;
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(ESCAPE_CHARACTER) && this.handleCommand(message.substring(1))) {
            event.setCanceled(true); // Отменяем отправку сообщения в чат
        }

    }

    public boolean handleCommand(String command) {
        System.out.println(command);
        String[] args = command.split("[ ]+");
        if (args.length <= 0) {
            return false;
        } else {
            Command cmd = this.commands.getCommand(args[0]);
            if (cmd == null) {
                return false;
            } else {
                String ret = cmd.execute(args);
                if (ret != null) {
                    ChatUtils.sendMessage(ret);
                }


                return true;
            }
        }
    }

    public boolean defaultState() {
        return true;
    }
}
