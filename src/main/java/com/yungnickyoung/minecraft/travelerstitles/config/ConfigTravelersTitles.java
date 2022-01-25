package com.yungnickyoung.minecraft.travelerstitles.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class ConfigTravelersTitles {
    @ConfigEntry.Category("Biome Title Settings")
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ConfigBiomes biomes = new ConfigBiomes();

    @ConfigEntry.Category("Dimension Title Settings")
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ConfigDimensions dimensions = new ConfigDimensions();

    @ConfigEntry.Category("Sound Settings")
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 3)
    public ConfigSound sound = new ConfigSound();
}
