package com.example.examplemod.Commands.UtilsCo;

import com.example.examplemod.Commands.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class Commands {
    private Map<String, Command> commands = new HashMap();

    public Commands() {
       // this.registerCommand(new GetCommand());
        this.registerCommand(new LookAtCommand());
        this.registerCommand(new ClearBindCommand());
        this.registerCommand(new ComCommand());
        this.registerCommand(new ChatGptCommand());


//        this.registerCommand(new HudBind());
    }

    public void registerCommand(Command cmd) {
        this.commands.put(cmd.getName(), cmd);
    }

    public Command getCommand(String name) {
        return (Command)this.commands.get(name);
    }

    public Collection<Command> getCommands() {
        return this.commands.values();
    }
}
