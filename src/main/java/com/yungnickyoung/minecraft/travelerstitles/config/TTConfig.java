package com.yungnickyoung.minecraft.travelerstitles.config;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name="travelerstitles-fabric-1_18")
public class TTConfig implements ConfigData {
    @ConfigEntry.Category("Traveler's Titles")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigTravelersTitles travelersTitles = new ConfigTravelersTitles();

    /**
     * Bakes in updated config values.
     */
    @Override
    public void validatePostLoad() {
        // Biome
        TravelersTitles.titleManager.biomeTitleRenderer.maxRecentListSize = travelersTitles.biomes.recentBiomeCacheSize;
        TravelersTitles.titleManager.biomeTitleRenderer.enabled = travelersTitles.biomes.enabled;
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeInTicks = travelersTitles.biomes.textFadeInTime;
        TravelersTitles.titleManager.biomeTitleRenderer.titleDisplayTime = travelersTitles.biomes.textDisplayTime;
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeOutTicks = travelersTitles.biomes.textFadeOutTime;
        TravelersTitles.titleManager.biomeTitleRenderer.titleDefaultTextColor = travelersTitles.biomes.textColor;
        TravelersTitles.titleManager.biomeTitleRenderer.showTextShadow = travelersTitles.biomes.renderShadow;
        TravelersTitles.titleManager.biomeTitleRenderer.titleTextSize = travelersTitles.biomes.textSize;
        TravelersTitles.titleManager.biomeTitleRenderer.titleXOffset = travelersTitles.biomes.textXOffset;
        TravelersTitles.titleManager.biomeTitleRenderer.titleYOffset = travelersTitles.biomes.textYOffset;
        TravelersTitles.titleManager.biomeTitleRenderer.isTextCentered = travelersTitles.biomes.centerText;

        // Dimension
        TravelersTitles.titleManager.dimensionTitleRenderer.maxRecentListSize = 1;
        TravelersTitles.titleManager.dimensionTitleRenderer.enabled = travelersTitles.dimensions.enabled;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeInTicks = travelersTitles.dimensions.textFadeInTime;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDisplayTime = travelersTitles.dimensions.textDisplayTime;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeOutTicks = travelersTitles.dimensions.textFadeOutTime;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor = travelersTitles.dimensions.textColor;
        TravelersTitles.titleManager.dimensionTitleRenderer.showTextShadow = travelersTitles.dimensions.renderShadow;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleTextSize = travelersTitles.dimensions.textSize;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleXOffset = travelersTitles.dimensions.textXOffset;
        TravelersTitles.titleManager.dimensionTitleRenderer.titleYOffset = travelersTitles.dimensions.textYOffset;
        TravelersTitles.titleManager.dimensionTitleRenderer.isTextCentered = travelersTitles.dimensions.centerText;

        // Parse & save biome blacklist
        String rawStringofList = travelersTitles.biomes.biomeBlacklist;
        int strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using empty list instead...");
            TravelersTitles.titleManager.blacklistedBiomes = new ArrayList<>();
            return;
        }

        // Parse string to list
        List<String> inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TravelersTitles.titleManager.blacklistedBiomes = Lists.newArrayList(inputListOfStrings);

        // Parse & save dimension blacklist
        rawStringofList = travelersTitles.dimensions.dimensionBlacklist;
        strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Dimensions'. Using empty list instead...");
            TravelersTitles.titleManager.blacklistedDimensions = new ArrayList<>();
            return;
        }

        // Parse string to list
        inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TravelersTitles.titleManager.blacklistedDimensions = Lists.newArrayList(inputListOfStrings);
    }
}
