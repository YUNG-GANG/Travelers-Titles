package com.yungnickyoung.minecraft.travelerstitles.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigDimensions {
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textFadeInTime = 10;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textDisplayTime = 70;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public int textFadeOutTime = 20;

    @ConfigEntry.Gui.Tooltip()
    public String textColor = "ffffff";

    @ConfigEntry.Gui.Tooltip()
    public float textSize = 3.0f;

    @ConfigEntry.Gui.Tooltip()
    public boolean renderShadow = true;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public float textYOffset = -32.0f;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public float textXOffset = 0;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public String dimensionBlacklist = "[]";

    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean centerText = true;

    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean onlyUpdateAtSurface = false;
}