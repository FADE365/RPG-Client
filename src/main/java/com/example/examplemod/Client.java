package com.example.examplemod;

import com.example.examplemod.Module.AUTODUPE.AutoDup;
import com.example.examplemod.Module.AUTODUPE.PacketInterception;
import com.example.examplemod.Module.CLIENT.ChatRecorder;
import com.example.examplemod.Module.CLIENT.Hud;
import com.example.examplemod.Module.CLIENT.OnChatModule;
import com.example.examplemod.Module.CLIENT.Panic;
import com.example.examplemod.Module.COMBAT.*;
import com.example.examplemod.Module.EXPLOIT.FakeCreative;
import com.example.examplemod.Module.MOVEMENT.*;
import com.example.examplemod.Module.Module;
import com.example.examplemod.Module.PLAYER.AutoTotem;
import com.example.examplemod.Module.PLAYER.BlockReach;
import com.example.examplemod.Module.RENDER.*;
import com.example.examplemod.Utils.CommandUtils;
import font.FontUtils;
import me.FADE.clickgui.ClickGuiScreen;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.input.Keyboard.KEY_RSHIFT;

public class Client {
    public static final Object DEFAULT_HUD_KEY = KEY_RSHIFT;
    public static String PlayerName = Minecraft.getMinecraft().getSession().getUsername();
    public static String name = "RPGClient 1.12.2 | " + PlayerName;
    public static String cName = "RPG \u00A7aClient";
    public static CopyOnWriteArrayList<Module> modules  = new CopyOnWriteArrayList<Module>();
    public static ClickGuiScreen clickGui;

    public static void startup(){
        Display.setTitle(name);

        modules.add(new Hud());

     // modules.add(new AutoDisconnect());

        modules.add(new PacketInterception());


        modules.add(new CommandUtils());
        modules.add(new FakeCreative());
        modules.add(new TriggerWizer());
        modules.add(new RenderPlayer());
        modules.add(new OnChatModule());
        modules.add(new ChatRecorder());
       // modules.add(new ChestStealer());
        modules.add(new LookAtBlock());
        modules.add(new AttackTrace());
        modules.add(new BlockReach());
        modules.add(new TriggerBot());
        modules.add(new FullBright());
        modules.add(new WaterLeave());
        modules.add(new SpawnerESP());
        modules.add(new AutoTotem());
        modules.add(new ViewModel());
        modules.add(new HightJump());
        modules.add(new TargetHUD());
        modules.add(new Particles());
        modules.add(new TunnelESP());
        modules.add(new Surround());
        modules.add(new GoDonkey());
        modules.add(new KillAura());
        modules.add(new Rotation());
        modules.add(new ViewLock());
        modules.add(new ChestESP());
        modules.add(new AirJump());
        modules.add(new Tracers());
        modules.add(new GlowESP());
        modules.add(new AntiBot());
        modules.add(new AutoDup());
        modules.add(new BoatFly());
        modules.add(new AimBot());
        modules.add(new BoxESP());
        modules.add(new Sprint());
        modules.add(new Spider());
        modules.add(new HitBox());
        modules.add(new Glide());
        modules.add(new Jesus());
        modules.add(new Speed());
        modules.add(new Panic());
        modules.add(new Fly());

        /*

        */

        clickGui = new ClickGuiScreen();

        FontUtils.bootstrap();
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getCategory().name().equalsIgnoreCase(c.name())) {
                mods.add(m);
            }
        }
        return  mods;
    }

    public static void KeyPress(int key) {
        for (Module m : modules) {
            if (m.getKey() == key) {
                System.out.println(key);
                m.toggle();
            }
        }
    }
}
