package com.yungnickyoung.minecraft.travelerstitles.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigSound {
    @ConfigEntry.Gui.Tooltip()
    public float biomeVolume = 1.0f;

    @ConfigEntry.Gui.Tooltip()
    public float biomePitch = 1.0f;

    @ConfigEntry.Gui.Tooltip()
    public float dimensionVolume = 1.0f;

    @ConfigEntry.Gui.Tooltip()
    public float dimensionPitch = 1.0f;
}

