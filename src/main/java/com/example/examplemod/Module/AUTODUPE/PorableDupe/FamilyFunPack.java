package com.example.examplemod.Module.AUTODUPE.PorableDupe;

import com.example.examplemod.Module.AUTODUPE.PorableDupe.commands.AdCommand;
import com.example.examplemod.Module.AUTODUPE.PorableDupe.modules.Modules;
import com.example.examplemod.Module.AUTODUPE.PorableDupe.network.NetworkHandler;
import com.example.examplemod.Utils.ChatUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@Mod(modid = FamilyFunPack.MODID, name = FamilyFunPack.NAME, version = FamilyFunPack.VERSION)
@SideOnly(Side.CLIENT)
public class FamilyFunPack
{
    public static final String MODID = "fade";
    public static final String NAME = "FadeSide";
    public static final String VERSION = "0.1";

    public static FamilyFunPack INSTANCE;

    private static NetworkHandler networkHandler;
    private static Modules modules;

    AdCommand autodupe = new AdCommand();

    private File confFile;

    /* Get NetworkHandler, for registering packets listeners */
    public static NetworkHandler getNetworkHandler() {
        return FamilyFunPack.networkHandler;
    }

    /* Get all Modules */
    public static Modules getModules() {
        return FamilyFunPack.modules;
    }




    /* Print message in chat */
    public static void printMessage(String msg) {
        ChatUtils.sendMessage(msg);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        this.confFile = event.getSuggestedConfigurationFile();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(event.getSide() == Side.CLIENT) {

            // Init network handler
            FamilyFunPack.networkHandler = new NetworkHandler();

            // load modules configuration
            FamilyFunPack.modules = new Modules(this.confFile);

            // register network
            MinecraftForge.EVENT_BUS.register(FamilyFunPack.networkHandler);

            MinecraftForge.EVENT_BUS.register(autodupe);

        }
    }
}
