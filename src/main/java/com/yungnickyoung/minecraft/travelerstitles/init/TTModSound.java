package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

public class TTModSound {
    public static final SoundEvent BIOME = createSoundEvent("biome");
    public static final SoundEvent DIMENSION = createSoundEvent("dimension");
    public static final SoundEvent WAYSTONE = createSoundEvent("waystone");

    public static void init() {
        MinecraftForge.EVENT_BUS.addGenericListener(SoundEvent.class, TTModSound::registerSoundEvents);
    }

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation id = new ResourceLocation(TravelersTitles.MOD_ID, soundName);
        return new SoundEvent(id).setRegistryName(id);
    }

    private static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(BIOME);
        event.getRegistry().register(DIMENSION);
        event.getRegistry().register(WAYSTONE);
    }
}
