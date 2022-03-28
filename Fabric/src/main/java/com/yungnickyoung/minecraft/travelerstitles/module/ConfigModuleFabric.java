package com.yungnickyoung.minecraft.travelerstitles.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.InteractionResult;

import java.util.ArrayList;
import java.util.List;

public class ConfigModuleFabric {
    private static TTConfigFabric fabricConfig;

    public static void init() {
        AutoConfig.register(TTConfigFabric.class, Toml4jConfigSerializer::new);
        AutoConfig.getConfigHolder(TTConfigFabric.class).registerSaveListener(ConfigModuleFabric::updateConfig);
        AutoConfig.getConfigHolder(TTConfigFabric.class).registerLoadListener(ConfigModuleFabric::updateConfig);
        fabricConfig = AutoConfig.getConfigHolder(TTConfigFabric.class).get();
        updateConfig();
    }

    public static void updateConfig() {
        bakeConfig(fabricConfig);
        ConfigModule.updateRenderersFromConfig();
    }

    private static InteractionResult updateConfig(ConfigHolder<TTConfigFabric> configHolder, TTConfigFabric configFabric) {
        bakeConfig(configFabric);
        ConfigModule.updateRenderersFromConfig();
        return InteractionResult.SUCCESS;
    }

    public static void bakeConfig(TTConfigFabric configFabric) {
        TravelersTitlesCommon.CONFIG.biomes.enabled = configFabric.biomes.enabled;
        TravelersTitlesCommon.CONFIG.biomes.textFadeInTime = configFabric.biomes.textFadeInTime;
        TravelersTitlesCommon.CONFIG.biomes.textDisplayTime = configFabric.biomes.textDisplayTime;
        TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime = configFabric.biomes.textFadeOutTime;
        TravelersTitlesCommon.CONFIG.biomes.textCooldownTime = configFabric.biomes.textCooldownTime;
        TravelersTitlesCommon.CONFIG.biomes.textColor = configFabric.biomes.textColor;
        TravelersTitlesCommon.CONFIG.biomes.textSize = configFabric.biomes.textSize;
        TravelersTitlesCommon.CONFIG.biomes.renderShadow = configFabric.biomes.renderShadow;
        TravelersTitlesCommon.CONFIG.biomes.textYOffset = configFabric.biomes.textYOffset;
        TravelersTitlesCommon.CONFIG.biomes.textXOffset = configFabric.biomes.textXOffset;
        TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize = configFabric.biomes.recentBiomeCacheSize;
        TravelersTitlesCommon.CONFIG.biomes.centerText = configFabric.biomes.centerText;
        TravelersTitlesCommon.CONFIG.biomes.resetBiomeCacheOnDimensionChange = configFabric.biomes.resetBiomeCacheOnDimensionChange;
        TravelersTitlesCommon.CONFIG.biomes.onlyUpdateAtSurface = configFabric.biomes.onlyUpdateAtSurface;
        TravelersTitlesCommon.CONFIG.biomes.biomeBlacklist = parseList(configFabric.biomes.biomeBlacklist, "Blacklisted Biomes");
        TravelersTitlesCommon.CONFIG.dimensions.enabled = configFabric.dimensions.enabled;
        TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime = configFabric.dimensions.textFadeInTime;
        TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime = configFabric.dimensions.textDisplayTime;
        TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime = configFabric.dimensions.textFadeOutTime;
        TravelersTitlesCommon.CONFIG.dimensions.textColor = configFabric.dimensions.textColor;
        TravelersTitlesCommon.CONFIG.dimensions.textSize = configFabric.dimensions.textSize;
        TravelersTitlesCommon.CONFIG.dimensions.renderShadow = configFabric.dimensions.renderShadow;
        TravelersTitlesCommon.CONFIG.dimensions.textYOffset = configFabric.dimensions.textYOffset;
        TravelersTitlesCommon.CONFIG.dimensions.textXOffset = configFabric.dimensions.textXOffset;
        TravelersTitlesCommon.CONFIG.dimensions.centerText = configFabric.dimensions.centerText;
        TravelersTitlesCommon.CONFIG.dimensions.onlyUpdateAtSurface = configFabric.dimensions.onlyUpdateAtSurface;
        TravelersTitlesCommon.CONFIG.dimensions.dimensionBlacklist = parseList(configFabric.dimensions.dimensionBlacklist, "Blacklisted Dimensions");
        TravelersTitlesCommon.CONFIG.sound.biomeVolume = configFabric.sound.biomeVolume;
        TravelersTitlesCommon.CONFIG.sound.biomePitch = configFabric.sound.biomePitch;
        TravelersTitlesCommon.CONFIG.sound.dimensionVolume = configFabric.sound.dimensionVolume;
        TravelersTitlesCommon.CONFIG.sound.dimensionPitch = configFabric.sound.dimensionPitch;
    }

    private static List<String> parseList(String rawStringOfList, String settingName) {
        int strLen = rawStringOfList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringOfList.charAt(0) != '[' || rawStringOfList.charAt(strLen - 1) != ']') {
            TravelersTitlesCommon.LOGGER.error("INVALID VALUE FOR SETTING '" + settingName + "'. Using empty list instead...");
            return new ArrayList<>();
        }

        return Lists.newArrayList(rawStringOfList.substring(1, strLen - 1).split(",\\s*"));
    }
}
