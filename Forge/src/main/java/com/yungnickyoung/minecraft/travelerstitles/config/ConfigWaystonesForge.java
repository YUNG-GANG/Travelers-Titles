package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigWaystonesForge {
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
    public final ForgeConfigSpec.ConfigValue<Integer> recentWaystoneCacheSize;
    public final ForgeConfigSpec.ConfigValue<Boolean> centerText;
    public final ForgeConfigSpec.ConfigValue<Boolean> resetWaystoneCacheOnDimensionChange;
    public final ForgeConfigSpec.ConfigValue<Integer> range;
    public final ForgeConfigSpec.ConfigValue<Boolean> waystonesOverrideBiomeTitle;
    public final ForgeConfigSpec.ConfigValue<Boolean> onlyUpdateAtSurface;

    public ConfigWaystonesForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                "# Waystone title settings. Only used if the Waystones mod is installed.\n" +
                "##########################################################################################################")
            .push("Waystone Titles");

        enabled = BUILDER
            .define("Enable Waystone Titles", true);

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
                " The minimum amount of time in ticks that must pass after a Waystone title is displayed before\n" +
                " another can be displayed.\n" +
                " Useful for preventing the player from being spammed if they are traveling quickly.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 80")
            .define("Text Cooldown Time", 80);

        textColor = BUILDER
            .comment(
                " The text's default RGB color.\n" +
                " Default: \"c2b740\"")
            .define("Default Text Color", "c2b740");

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

        recentWaystoneCacheSize = BUILDER
            .comment(
                " Traveler's Titles tracks a list of Waystones the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few Waystones.\n" +
                " This is the size of that list.\n" +
                " For example, if this value is 5, then your 5 most recent Waystones will be saved.\n" +
                " Default: 3")
            .define("Number of Most Recent Waystones Saved", 3);

        centerText = BUILDER
            .comment(
                " Whether or not the Waystone text should be centered on the screen.\n" +
                " The Text X Offset and Text Y Offset options are relative to the center of the screen if this is enabled.\n" +
                " Default: true")
            .define("Center Title", true);

        resetWaystoneCacheOnDimensionChange = BUILDER
            .comment(
                " Traveler's Titles tracks a list of Waystones the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few Waystones.\n" +
                " This option determines whether or not that list should be cleared every time\n" +
                " the player changes dimensions.\n" +
                " Default: true")
            .define("Reset Waystone Cache When Changing Dimensions", true);

        range = BUILDER
            .comment(
                " The distance from a Waystone (in blocks) at which the Waystone's title will trigger.\n" +
                " Default: 30")
            .define("Waystone Title Range", 30);

        waystonesOverrideBiomeTitle = BUILDER
            .comment(
                " Whether or not Waystone titles should override Biome titles.\n" +
                " That is, if a player enters the area for a Waystone while also entering a new biome,\n" +
                " the Waystone title will take precedence.\n" +
                " Default: true")
            .define("Waystone Titles Override Biome Titles", true);

        onlyUpdateAtSurface = BUILDER
            .comment(
                " If enabled, dimensions without ceilings (like the Overworld) will only display Waystone titles when the player is exposed to the skylight.\n" +
                " This prevents Waystone titles from showing while the player is underground.\n" +
                " Default: false")
            .define("Only Show Waystone Titles When Exposed To Skylight", false);

        BUILDER.pop();
    }
}
