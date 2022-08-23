package com.yungnickyoung.minecraft.travelerstitles.module;

import net.minecraft.core.Registry;

public class SoundModuleFabric {
    public static void init() {
        registerSoundEvents();
    }

    private static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, SoundModule.BIOME.getLocation(), SoundModule.BIOME);
        Registry.register(Registry.SOUND_EVENT, SoundModule.DIMENSION.getLocation(), SoundModule.DIMENSION);
        Registry.register(Registry.SOUND_EVENT, SoundModule.WAYSTONE.getLocation(), SoundModule.WAYSTONE);
    }
}
