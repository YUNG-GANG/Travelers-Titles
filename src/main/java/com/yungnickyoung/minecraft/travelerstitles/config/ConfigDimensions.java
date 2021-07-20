package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigDimensions {
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

    public ConfigDimensions(final ForgeConfigSpec.Builder BUILDER) {
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
                " A value of 0.0 refers to the center of the screen.\n" +
                " Default: -32.0")
            .define("Text Y Offset", -32.0);

        textXOffset = BUILDER
            .comment(
                " The text's horizontal position on the screen.\n" +
                " If Horizontally Center Title is enabled, this setting is disregarded.\n" +
                " A value of 0.0 refers to the center of the screen.\n" +
                " Default: 0")
            .define("Text X Offset", 0.0);

        dimensionBlacklist = BUILDER
            .comment(
                " Dimensions that should not have any title displayed when the player enters them.\n" +
                " Example: \"[minecraft:overworld, minecraft:the_nether]\"\n" +
                " Default: \"[]\"")
            .define("Blacklisted Dimensions", "[]");

        centerText = BUILDER
            .comment(
                " Whether or not the dimension text should be horizontally centered on the screen.\n" +
                " If enabled, the Text X Offset setting is disregarded.\n" +
                " Default: true")
            .define("Horizontally Center Title", true);

        BUILDER.pop();
    }
}