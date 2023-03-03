package com.yungnickyoung.minecraft.travelerstitles.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="travelerstitles-fabric-1_19_3")
public class TTConfigFabric implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ConfigBiomesFabric biomes = new ConfigBiomesFabric();

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    public ConfigDimensionsFabric dimensions = new ConfigDimensionsFabric();

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 3)
    public ConfigSoundFabric sound = new ConfigSoundFabric();
}