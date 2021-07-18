package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TTConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigBiomes biomes;
    public static final ConfigDimensions dimensions;

    static {
        BUILDER.push("Traveler's Titles");

        biomes = new ConfigBiomes(BUILDER);
        dimensions = new ConfigDimensions(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
