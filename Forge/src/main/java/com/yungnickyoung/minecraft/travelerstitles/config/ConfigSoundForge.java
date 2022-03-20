package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigSoundForge {
    public final ForgeConfigSpec.ConfigValue<Double> biomeVolume;
    public final ForgeConfigSpec.ConfigValue<Double> biomePitch;
    public final ForgeConfigSpec.ConfigValue<Double> dimensionVolume;
    public final ForgeConfigSpec.ConfigValue<Double> dimensionPitch;
    public final ForgeConfigSpec.ConfigValue<Double> waystoneVolume;
    public final ForgeConfigSpec.ConfigValue<Double> waystonePitch;

    public ConfigSoundForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                "# Sound settings. These will only be used if you add custom sounds via a resource pack.\n" +
                "# For information on how to do this, visit the CurseForge page.\n" +
                "##########################################################################################################")
            .push("Custom Sound Settings");

        biomeVolume = BUILDER
            .comment(
                " The volume of the sound that plays when a biome title displays.\n" +
                " Default: 1.0")
            .define("Biome Sound Effect Volume", 1.0);

        biomePitch = BUILDER
            .comment(
                " The pitch of the sound that plays when a biome title displays.\n" +
                " Default: 1.0")
            .define("Biome Sound Effect Pitch", 1.0);

        dimensionVolume = BUILDER
            .comment(
                " The volume of the sound that plays when a dimension title displays.\n" +
                " Default: 1.0")
            .define("Dimension Sound Effect Volume", 1.0);

        dimensionPitch = BUILDER
            .comment(
                " The pitch of the sound that plays when a dimension title displays.\n" +
                " Default: 1.0")
            .define("Dimension Sound Effect Pitch", 1.0);

        waystoneVolume = BUILDER
            .comment(
                " The volume of the sound that plays when a Waystone title displays.\n" +
                " The Waystones mod must be installed for this to have any effect.\n" +
                " Default: 1.0")
            .define("Waystone Sound Effect Volume", 1.0);

        waystonePitch = BUILDER
            .comment(
                " The pitch of the sound that plays when a Waystone title displays.\n" +
                " The Waystones mod must be installed for this to have any effect.\n" +
                " Default: 1.0")
            .define("Waystone Sound Effect Pitch", 1.0);

        BUILDER.pop();
    }
}

