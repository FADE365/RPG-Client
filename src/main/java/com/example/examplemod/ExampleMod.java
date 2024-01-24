package com.example.examplemod;

import com.example.examplemod.Menu.onGuiOpenEvent;
import com.example.examplemod.Module.ModSettings;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Module.RENDER.FullBright;
import com.example.examplemod.Module.UI.ui;
import com.example.examplemod.keys.key;
import me.FADE.clickgui.ClickGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.Field;

import static com.example.examplemod.Client.modules;
import static com.example.examplemod.Module.ModSettings.loadSettings;
import static com.example.examplemod.Module.Module.mc;
import static com.example.examplemod.Module.Module.saveSettings;
@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "rpg";
    public static final String NAME = "RPGClient Mod";
    public static final String VERSION = "3.9";

    private static Logger logger;
    public static ModSettings modSettings;
    public static ExampleMod instance;

    public static ClickGuiScreen clickGui;
    //public static final SoundEvent BUTTON_PRESS = new SoundEvent(new ResourceLocation(MODID, "button_press")).setRegistryName("button_press");
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Display.setTitle("Loading... " + Client.name);
        logger = event.getModLog();
        modSettings = loadSettings(new File("mod_settings.json"));
    }


    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        Client.startup();
        MinecraftForge.EVENT_BUS.register(new key());
        MinecraftForge.EVENT_BUS.register(new ui());
        MinecraftForge.EVENT_BUS.register(new onGuiOpenEvent());
        // Init network handler
//        NetworkHandler networkHandler = new NetworkHandler();
        // register network
//        MinecraftForge.EVENT_BUS.register(networkHandler);

        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        mc.gameSettings.gammaSetting = FullBright.FullBrightGamma;

        // Загрузка состояний модулей из файла и включение/выключение модулей
        ModSettings modSettings = ModSettings.loadSettings(new File("mod_settings.json"));
        for (Module module : modules) {
            Boolean isEnabled = modSettings.moduleStates.get(module.getName());
            if (isEnabled != null) {
                module.setToggled(isEnabled); // Включение или выключение модуля на основе состояния из файла
            }
        }
    }



    public static void setSession(Session s) {
        Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();

        try {
            Field session = null;

            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;

                }
            }

            if (session == null) {
                throw new IllegalStateException("Session Null");
            }

            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);

            Client.name = "RPGClient 1.12.2";
            Display.setTitle(Client.name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void shutdown() {
        for (Module module : modules) {
            modSettings.moduleStates.put(module.getName(), module.isEnabled());
        }
        saveSettings(modSettings, new File("mod_settings.json"));
    }

}
