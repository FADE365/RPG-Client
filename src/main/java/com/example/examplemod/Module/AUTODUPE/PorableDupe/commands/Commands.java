package com.example.examplemod.Module.AUTODUPE.PorableDupe.commands;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* Commands record */

@SideOnly(Side.CLIENT)
public class Commands {
  private final Map<String, Command> commands;

  /* I'm too lazy to develop interfaces so let's add a lot of commands */

  public Commands() {
    this.commands = new HashMap<String, Command>();
    this.registerCommand(new RollbackCommand());
    this.registerCommand(new AdCommand());
    this.registerCommand(new ChestCommand());
    this.registerCommand(new EndCommand());
  }

  public void registerCommand(Command cmd) {
    this.commands.put(cmd.getName(), cmd);
  }

  public Command getCommand(String name) {
    return this.commands.get(name);
  }

  public Collection<Command> getCommands() {
    return this.commands.values();
  }
}
