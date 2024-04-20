package com.yungnickyoung.minecraft.travelerstitles.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class TTConfigNeoForge {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ConfigBiomesNeoForge biomes;
    public static final ConfigDimensionsNeoForge dimensions;
    public static final ConfigWaystonesNeoForge waystones;
    public static final ConfigSoundNeoForge sound;

    static {
        BUILDER.push("Traveler's Titles");

        biomes = new ConfigBiomesNeoForge(BUILDER);
        dimensions = new ConfigDimensionsNeoForge(BUILDER);
        waystones = new ConfigWaystonesNeoForge(BUILDER);
        sound = new ConfigSoundNeoForge(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
