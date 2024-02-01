package com.example.examplemod.Commands.UtilsCo;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Command {
    private String name;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void onDisconnect() {
    }

    public abstract String usage();

    public String getUsage() {
        return "Usage: " + this.usage();
    }

    public abstract Object execute(String[] var1);
}
