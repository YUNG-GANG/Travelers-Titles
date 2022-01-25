package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class TTModSound {
    public static final SoundEvent BIOME = createSoundEvent("biome");
    public static final SoundEvent DIMENSION = createSoundEvent("dimension");

    private static SoundEvent createSoundEvent(final String soundName) {
        return new SoundEvent(new ResourceLocation(TravelersTitles.MOD_ID, soundName));
    }

    public static void init() {
        registerSoundEvents();
    }

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, BIOME.getLocation(), BIOME);
        Registry.register(Registry.SOUND_EVENT, DIMENSION.getLocation(), DIMENSION);
    }
}
