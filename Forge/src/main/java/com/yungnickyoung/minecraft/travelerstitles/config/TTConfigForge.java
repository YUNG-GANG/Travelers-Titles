package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TTConfigForge {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigBiomesForge biomes;
    public static final ConfigDimensionsForge dimensions;
    public static final ConfigWaystonesForge waystones;
    public static final ConfigSoundForge sound;

    static {
        BUILDER.push("Traveler's Titles");

        biomes = new ConfigBiomesForge(BUILDER);
        dimensions = new ConfigDimensionsForge(BUILDER);
        waystones = new ConfigWaystonesForge(BUILDER);
        sound = new ConfigSoundForge(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
