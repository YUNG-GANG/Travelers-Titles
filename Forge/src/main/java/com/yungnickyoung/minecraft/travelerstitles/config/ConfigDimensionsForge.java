package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigDimensionsForge {
    public final ForgeConfigSpec.ConfigValue<Boolean> enabled;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeInTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textDisplayTime;
    public final ForgeConfigSpec.ConfigValue<Integer> textFadeOutTime;
    public final ForgeConfigSpec.ConfigValue<String> textColor;
    public final ForgeConfigSpec.ConfigValue<Double> textSize;
    public final ForgeConfigSpec.ConfigValue<Boolean> renderShadow;
    public final ForgeConfigSpec.ConfigValue<Double> textYOffset;
    public final ForgeConfigSpec.ConfigValue<Double> textXOffset;
    public final ForgeConfigSpec.ConfigValue<String> dimensionBlacklist;
    public final ForgeConfigSpec.ConfigValue<Boolean> centerText;
    public final ForgeConfigSpec.ConfigValue<Boolean> onlyUpdateAtSurface;

    public ConfigDimensionsForge(final ForgeConfigSpec.Builder BUILDER) {
        BUILDER
            .comment(
                "##########################################################################################################\n" +
                "# Dimension Title settings.\n" +
                "##########################################################################################################")
            .push("Dimension Titles");

        enabled = BUILDER
            .define("Enable Dimension Titles", true);

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
                " Default: 70")
            .define("Text Display Time", 70);

        textFadeOutTime = BUILDER
            .comment(
                " How long the fade-out text effect lasts, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 20")
            .define("Text Fade-Out Time", 20);

        textColor = BUILDER
            .comment(
                " The text's default RGB color.\n" +
                " Default: \"ffffff\"")
            .define("Default Text Color", "ffffff");

        textSize = BUILDER
            .comment(
                " The text's scale.\n" +
                " Default: 3.0")
            .define("Text Size", 3.0);

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
                " Default: -32.0")
            .define("Text Y Offset", -32.0);

        textXOffset = BUILDER
            .comment(
                " The text's horizontal position on the screen.\n" +
                " If Horizontally Center Title is enabled, this number is relative to the center of the screen.\n" +
                " If Horizontally Center Title is disabled, this number is relative to the left side of the screen.\n" +
                " Default: 0.0")
            .define("Text X Offset", 0.0);

        dimensionBlacklist = BUILDER
            .comment(
                " Dimensions that should not have any title displayed when the player enters them.\n" +
                " Example: \"[minecraft:overworld, minecraft:the_nether]\"\n" +
                " Default: \"[]\"")
            .define("Blacklisted Dimensions", "[]");

        centerText = BUILDER
            .comment(
                " Whether or not the dimension text should be centered on the screen.\n" +
                " The Text X Offset and Text Y Offset options are relative to the center of the screen if this is enabled.\n" +
                " Default: true")
            .define("Center Title", true);

        onlyUpdateAtSurface = BUILDER
            .comment(
                " If enabled, dimensions without ceilings (like the Overworld) will only display dimension titles when the player is exposed to the skylight.\n" +
                " This prevents dimension titles from showing while the player is underground.\n" +
                " Default: false")
            .define("Only Show Dimension Titles When Exposed To Skylight", false);

        BUILDER.pop();
    }
}