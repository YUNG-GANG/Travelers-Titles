package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

public class SoundModuleForge {
    public static void init() {
        MinecraftForge.EVENT_BUS.addGenericListener(SoundEvent.class, SoundModuleForge::registerSoundEvents);
        SoundModule.BIOME = createSoundEvent("biome");
        SoundModule.DIMENSION = createSoundEvent("dimension");
        SoundModule.WAYSTONE = createSoundEvent("waystone");
    }

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation id = new ResourceLocation(TravelersTitlesCommon.MOD_ID, soundName);
        return new SoundEvent(id).setRegistryName(id);
    }

    private static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(SoundModule.BIOME);
        event.getRegistry().register(SoundModule.DIMENSION);
        event.getRegistry().register(SoundModule.WAYSTONE);
    }
}
