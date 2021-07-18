package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigGeneral {
    public final ForgeConfigSpec.ConfigValue<Boolean> onlyUpdateAtSurface;

    public ConfigGeneral(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                "# General settings.\n" +
                "##########################################################################################################")
            .push("General");

        onlyUpdateAtSurface = BUILDER
            .comment(
                " If enabled, dimensions without ceilings (like the Overworld) will only display titles when the player is exposed to the skylight.\n" +
                " This prevents dimension & biome titles from showing while the user is underground.\n" +
                " Default: true")
            .define("Only Show Titles When Exposed To Skylight", true);

        BUILDER.pop();
    }
}
