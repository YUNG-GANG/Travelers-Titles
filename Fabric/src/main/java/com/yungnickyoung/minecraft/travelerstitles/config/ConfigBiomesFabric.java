package com.yungnickyoung.minecraft.travelerstitles.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigBiomesFabric {
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textFadeInTime = 10;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textDisplayTime = 50;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textFadeOutTime = 10;

    @ConfigEntry.Gui.Tooltip(count = 4)
    public int textCooldownTime = 80;

    @ConfigEntry.Gui.Tooltip()
    public String textColor = "ffffff";

    @ConfigEntry.Gui.Tooltip()
    public float textSize = 2.1f;

    @ConfigEntry.Gui.Tooltip()
    public boolean renderShadow = true;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public int textYOffset = -33;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public int textXOffset = 0;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public String biomeBlacklist = "[minecraft:the_end, minecraft:river, minecraft:frozen_river]";

    @ConfigEntry.Gui.Tooltip(count = 4)
    public int recentBiomeCacheSize = 5;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean centerText = true;

    @ConfigEntry.Gui.Tooltip(count = 4)
    public boolean resetBiomeCacheOnDimensionChange = true;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean onlyUpdateAtSurface = true;
}