package com.yungnickyoung.minecraft.travelerstitles.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesNeoForge;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfigNeoForge;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.ArrayList;
import java.util.List;

public class ConfigModuleNeoForge {
    public static void init() {
        // Register mod config with NeoForge
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TTConfigNeoForge.SPEC, "travelerstitles-neoforge-1_20_4.toml");
        NeoForge.EVENT_BUS.addListener(ConfigModuleNeoForge::onWorldLoad);
        TravelersTitlesNeoForge.loadingContextEventBus.addListener(ConfigModuleNeoForge::configChanged);
    }

    public static void onWorldLoad(LevelEvent.Load event) {
        bakeConfig();
        ConfigModule.updateRenderersFromConfig();
    }

    public static void configChanged(ModConfigEvent event) {
        ModConfig config = event.getConfig();

        // Bake config
        if (config.getSpec() == TTConfigNeoForge.SPEC) {
            bakeConfig();
            ConfigModule.updateRenderersFromConfig();
        }
    }

    public static void bakeConfig() {
        TravelersTitlesCommon.CONFIG.biomes.enabled = TTConfigNeoForge.biomes.enabled.get();
        TravelersTitlesCommon.CONFIG.biomes.textFadeInTime = TTConfigNeoForge.biomes.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textDisplayTime = TTConfigNeoForge.biomes.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime = TTConfigNeoForge.biomes.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textCooldownTime = TTConfigNeoForge.biomes.textCooldownTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textColor = TTConfigNeoForge.biomes.textColor.get();
        TravelersTitlesCommon.CONFIG.biomes.textSize = TTConfigNeoForge.biomes.textSize.get();
        TravelersTitlesCommon.CONFIG.biomes.renderShadow = TTConfigNeoForge.biomes.renderShadow.get();
        TravelersTitlesCommon.CONFIG.biomes.textYOffset = TTConfigNeoForge.biomes.textYOffset.get();
        TravelersTitlesCommon.CONFIG.biomes.textXOffset = TTConfigNeoForge.biomes.textXOffset.get();
        TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize = TTConfigNeoForge.biomes.recentBiomeCacheSize.get();
        TravelersTitlesCommon.CONFIG.biomes.centerText = TTConfigNeoForge.biomes.centerText.get();
        TravelersTitlesCommon.CONFIG.biomes.resetBiomeCacheOnDimensionChange = TTConfigNeoForge.biomes.resetBiomeCacheOnDimensionChange.get();
        TravelersTitlesCommon.CONFIG.biomes.onlyUpdateAtSurface = TTConfigNeoForge.biomes.onlyUpdateAtSurface.get();
        TravelersTitlesCommon.CONFIG.biomes.biomeBlacklist = parseList(TTConfigNeoForge.biomes.biomeBlacklist.get(), "Blacklisted Biomes");
        TravelersTitlesCommon.CONFIG.dimensions.enabled = TTConfigNeoForge.dimensions.enabled.get();
        TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime = TTConfigNeoForge.dimensions.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime = TTConfigNeoForge.dimensions.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime = TTConfigNeoForge.dimensions.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textColor = TTConfigNeoForge.dimensions.textColor.get();
        TravelersTitlesCommon.CONFIG.dimensions.textSize = TTConfigNeoForge.dimensions.textSize.get();
        TravelersTitlesCommon.CONFIG.dimensions.renderShadow = TTConfigNeoForge.dimensions.renderShadow.get();
        TravelersTitlesCommon.CONFIG.dimensions.textYOffset = TTConfigNeoForge.dimensions.textYOffset.get();
        TravelersTitlesCommon.CONFIG.dimensions.textXOffset = TTConfigNeoForge.dimensions.textXOffset.get();
        TravelersTitlesCommon.CONFIG.dimensions.centerText = TTConfigNeoForge.dimensions.centerText.get();
        TravelersTitlesCommon.CONFIG.dimensions.onlyUpdateAtSurface = TTConfigNeoForge.dimensions.onlyUpdateAtSurface.get();
        TravelersTitlesCommon.CONFIG.dimensions.dimensionBlacklist = parseList(TTConfigNeoForge.dimensions.dimensionBlacklist.get(), "Blacklisted Dimensions");
        TravelersTitlesCommon.CONFIG.sound.biomeVolume = TTConfigNeoForge.sound.biomeVolume.get();
        TravelersTitlesCommon.CONFIG.sound.biomePitch = TTConfigNeoForge.sound.biomePitch.get();
        TravelersTitlesCommon.CONFIG.sound.dimensionVolume = TTConfigNeoForge.sound.dimensionVolume.get();
        TravelersTitlesCommon.CONFIG.sound.dimensionPitch = TTConfigNeoForge.sound.dimensionPitch.get();
        TravelersTitlesCommon.CONFIG.sound.waystoneVolume = TTConfigNeoForge.sound.waystoneVolume.get();
        TravelersTitlesCommon.CONFIG.sound.waystonePitch = TTConfigNeoForge.sound.waystonePitch.get();
        TravelersTitlesCommon.CONFIG.waystones.enabled = TTConfigNeoForge.waystones.enabled.get();
        TravelersTitlesCommon.CONFIG.waystones.textFadeInTime = TTConfigNeoForge.waystones.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textDisplayTime = TTConfigNeoForge.waystones.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textFadeOutTime = TTConfigNeoForge.waystones.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textCooldownTime = TTConfigNeoForge.waystones.textCooldownTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textColor = TTConfigNeoForge.waystones.textColor.get();
        TravelersTitlesCommon.CONFIG.waystones.textSize = TTConfigNeoForge.waystones.textSize.get();
        TravelersTitlesCommon.CONFIG.waystones.renderShadow = TTConfigNeoForge.waystones.renderShadow.get();
        TravelersTitlesCommon.CONFIG.waystones.textYOffset = TTConfigNeoForge.waystones.textYOffset.get();
        TravelersTitlesCommon.CONFIG.waystones.textXOffset = TTConfigNeoForge.waystones.textXOffset.get();
        TravelersTitlesCommon.CONFIG.waystones.recentWaystoneCacheSize = TTConfigNeoForge.waystones.recentWaystoneCacheSize.get();
        TravelersTitlesCommon.CONFIG.waystones.centerText = TTConfigNeoForge.waystones.centerText.get();
        TravelersTitlesCommon.CONFIG.waystones.resetWaystoneCacheOnDimensionChange = TTConfigNeoForge.waystones.resetWaystoneCacheOnDimensionChange.get();
        TravelersTitlesCommon.CONFIG.waystones.range = TTConfigNeoForge.waystones.range.get();
        TravelersTitlesCommon.CONFIG.waystones.waystonesOverrideBiomeTitle = TTConfigNeoForge.waystones.waystonesOverrideBiomeTitle.get();
        TravelersTitlesCommon.CONFIG.waystones.onlyUpdateAtSurface = TTConfigNeoForge.waystones.onlyUpdateAtSurface.get();
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
