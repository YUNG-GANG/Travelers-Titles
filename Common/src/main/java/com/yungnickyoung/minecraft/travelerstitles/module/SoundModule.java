package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundModule {
    public static final SoundEvent BIOME = registerSound("biome");
    public static final SoundEvent DIMENSION = registerSound("dimension");
    public static final SoundEvent WAYSTONE = registerSound("waystone");

    private static SoundEvent registerSound(String name) {
        ResourceLocation id = TravelersTitlesCommon.id(name);
        return SoundEvent.createVariableRangeEvent(id);
    }

    public static void init() {
        // Register all sound events
        register("biome", BIOME);
        register("dimension", DIMENSION);
        register("waystone", WAYSTONE);
    }

    private static void register(String name, SoundEvent soundEvent) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, TravelersTitlesCommon.id(name), soundEvent);
    }
}
