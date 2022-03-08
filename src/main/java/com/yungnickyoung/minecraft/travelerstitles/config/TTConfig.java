package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TTConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ConfigBiomes biomes;
    public static final ConfigDimensions dimensions;
//    public static final ConfigWaystones waystones;
    public static final ConfigSound sound;

    static {
        BUILDER.push("Traveler's Titles");

        biomes = new ConfigBiomes(BUILDER);
        dimensions = new ConfigDimensions(BUILDER);
//        waystones = new ConfigWaystones(BUILDER);
        sound = new ConfigSound(BUILDER);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
