package com.example.examplemod.Module.AUTODUPE.PorableDupe.commands;

import com.example.examplemod.Module.AUTODUPE.PorableDupe.FamilyFunPack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/* A Command */

@SideOnly(Side.CLIENT)
public abstract class Command {

  private final String name;

  public Command(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  /* When disconnecting from server */
  public void onDisconnect() {}


  public abstract String usage();

  public String getUsage() {
    return "Usage: " + this.usage();
  }

  // should some commands leak your coords
  protected boolean showDebugInfo() {
    return FamilyFunPack.getModules().getConfiguration().get("commands", "showDebugInfo", true).getBoolean();
  }

  // Execute a command
  // First argument is always command name
  public abstract String execute(String[] args);

}
