package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundModuleFabric {
    public static void init() {
        SoundModule.BIOME = createSoundEvent("biome");
        SoundModule.DIMENSION = createSoundEvent("dimension");
        SoundModule.WAYSTONE = createSoundEvent("waystone");
        registerSoundEvents();
    }

    private static SoundEvent createSoundEvent(final String soundName) {
        return new SoundEvent(new ResourceLocation(TravelersTitlesCommon.MOD_ID, soundName));
    }

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, SoundModule.BIOME.getLocation(), SoundModule.BIOME);
        Registry.register(Registry.SOUND_EVENT, SoundModule.DIMENSION.getLocation(), SoundModule.DIMENSION);
        Registry.register(Registry.SOUND_EVENT, SoundModule.WAYSTONE.getLocation(), SoundModule.WAYSTONE);
    }
}
