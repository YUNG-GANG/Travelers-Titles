package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigBiomes {
    public final ForgeConfigSpec.ConfigValue<Boolean> enabled;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeInTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textDisplayTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeOutTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textCooldownTime;
    public final ForgeConfigSpec.ConfigValue<String> textColor;
    public final ForgeConfigSpec.ConfigValue<Double> textSize;
    public final ForgeConfigSpec.ConfigValue<Boolean> renderShadow;
    public final ForgeConfigSpec.ConfigValue<Double> textYOffset;
    public final ForgeConfigSpec.ConfigValue<Double> textXOffset;
    public final ForgeConfigSpec.ConfigValue<String> biomeBlacklist;
    public final ForgeConfigSpec.ConfigValue<Integer> recentBiomeCacheSize;
    public final ForgeConfigSpec.ConfigValue<Boolean> centerText;

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

        textCooldownTime = BUILDER
            .comment(
                " The minimum amount of time in ticks that must pass after a biome title is displayed before\n" +
                " another can be displayed.\n" +
                " Useful for preventing the player from being spammed if they are traveling quickly.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 80")
            .define("Text Cooldown Time", 80);

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
                " If Horizontally Center Title is enabled, this number is relative to the center of the screen.\n" +
                " If Horizontally Center Title is disabled, this number is relative to the top of the screen.\n" +
                " Default: -33.0")
            .define("Text Y Offset", -33.0);

        textXOffset = BUILDER
            .comment(
                " The text's horizontal position on the screen.\n" +
                " If Horizontally Center Title is enabled, this number is relative to the center of the screen.\n" +
                " If Horizontally Center Title is disabled, this number is relative to the left side of the screen.\n" +
                " Default: 0.0")
            .define("Text X Offset", 0.0);

        biomeBlacklist = BUILDER
            .comment(
                " Biomes that should not have any title displayed when the player enters them.\n" +
                " Example: \"[minecraft:plains, minecraft:desert]\"\n" +
                " Default: \"[minecraft:the_end, minecraft:river, minecraft:frozen_river]\"")
            .define("Blacklisted Biomes", "[minecraft:the_end, minecraft:river, minecraft:frozen_river]");

        recentBiomeCacheSize = BUILDER
            .comment(
                " Traveler's Titles tracks a list of biomes the player most recently visited in order to\n" +
                " prevent the user from being spammed with titles when they move between the same few biomes.\n" +
                " This is the size of that list.\n" +
                " For example, if this value is 5, then your 5 most recent biomes will be saved.\n" +
                " Default: 5")
            .define("Number of Most Recent Biomes Saved", 5);

        centerText = BUILDER
            .comment(
                " Whether or not the biome text should be centered on the screen.\n" +
                " The Text X Offset and Text Y Offset options are relative to the center of the screen if this is enabled.\n" +
                " Default: true")
            .define("Center Title", true);

        BUILDER.pop();
    }
}
