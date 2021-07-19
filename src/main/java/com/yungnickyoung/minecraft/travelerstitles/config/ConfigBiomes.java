package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigBiomes {
    public final ForgeConfigSpec.ConfigValue<Boolean> enabled;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeInTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textDisplayTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeOutTime;
    public final ForgeConfigSpec.ConfigValue<String> textColor;
    public final ForgeConfigSpec.ConfigValue<Double> textSize;
    public final ForgeConfigSpec.ConfigValue<Boolean> renderShadow;
    public final ForgeConfigSpec.ConfigValue<Double> textYOffset;
    public final ForgeConfigSpec.ConfigValue<String> biomeBlacklist;

    public ConfigBiomes(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                "# Biome Title settings.\n" +
                "##########################################################################################################")
            .push("Biome Titles");

        enabled = BUILDER
            .define("Enable Biome Titles", true);

        textFadeInTime = BUILDER
            .comment(
                " How long the fade-in text effect lasts, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 10")
            .define("Text Fade-In Time", 10);

        textDisplayTime = BUILDER
            .comment(
                " How long the text displays, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 50")
            .define("Text Display Time", 50);

        textFadeOutTime = BUILDER
            .comment(
                " How long the fade-out text effect lasts, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 10")
            .define("Text Fade-Out Time", 10);

        textColor = BUILDER
            .comment(
                " The text's default RGB color.\n" +
                " Default: \"ffffff\"")
            .define("Default Text Color", "ffffff");

        textSize = BUILDER
            .comment(
                " The text's scale.\n" +
                " Default: 2.1")
            .define("Text Size", 2.1);

        renderShadow = BUILDER
            .comment(
                " If enabled, will render a shadow below the text making it easier to read.\n" +
                " Default: true")
            .define("Display Text Shadow", true);

        textYOffset = BUILDER
            .comment(
                " The text's vertical position on the screen.\n" +
                " Default: -33.0")
            .define("Text Y Offset", -33.0);

        biomeBlacklist = BUILDER
            .comment(
                " Biomes that should not have any title displayed when the player enters them.\n" +
                " Example: \"[minecraft:plains, minecraft:desert]\"\n" +
                " Default: \"[minecraft:the_end, minecraft:river]\"")
            .define("Blacklisted Biomes", "[minecraft:the_end, minecraft:river]");

        BUILDER.pop();
    }
}
