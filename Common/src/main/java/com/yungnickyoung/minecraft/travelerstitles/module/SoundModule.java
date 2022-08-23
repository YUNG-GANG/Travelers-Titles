package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundModule {
    public static SoundEvent BIOME = createSoundEvent("biome");
    public static SoundEvent DIMENSION = createSoundEvent("dimension");
    public static SoundEvent WAYSTONE = createSoundEvent("waystone");

    private static SoundEvent createSoundEvent(final String soundName) {
        return new SoundEvent(new ResourceLocation(TravelersTitlesCommon.MOD_ID, soundName));
    }
}
