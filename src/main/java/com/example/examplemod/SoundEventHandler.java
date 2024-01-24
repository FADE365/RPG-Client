package com.example.examplemod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class SoundEventHandler {
    // Регистрация SoundEvent
    public static final SoundEvent BUTTON_PRESS = new SoundEvent(new ResourceLocation( "button_press")).setRegistryName(new ResourceLocation( "button_press"));


    @SubscribeEvent
    public static void onSoundEventRegistration(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(BUTTON_PRESS);
    }
}
