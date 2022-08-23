package com.yungnickyoung.minecraft.travelerstitles.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfigForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class ConfigModuleForge {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TTConfigForge.SPEC, "travelerstitles-forge-1_19.toml");
        MinecraftForge.EVENT_BUS.addListener(ConfigModuleForge::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfigModuleForge::configChanged);
    }

    public static void onWorldLoad(LevelEvent.Load event) {
        bakeConfig();
        ConfigModule.updateRenderersFromConfig();
    }

    public static void configChanged(ModConfigEvent event) {
        ModConfig config = event.getConfig();

        // Bake config
        if (config.getSpec() == TTConfigForge.SPEC) {
            bakeConfig();
            ConfigModule.updateRenderersFromConfig();
        }
    }

    public static void bakeConfig() {
        TravelersTitlesCommon.CONFIG.biomes.enabled = TTConfigForge.biomes.enabled.get();
        TravelersTitlesCommon.CONFIG.biomes.textFadeInTime = TTConfigForge.biomes.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textDisplayTime = TTConfigForge.biomes.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime = TTConfigForge.biomes.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textCooldownTime = TTConfigForge.biomes.textCooldownTime.get();
        TravelersTitlesCommon.CONFIG.biomes.textColor = TTConfigForge.biomes.textColor.get();
        TravelersTitlesCommon.CONFIG.biomes.textSize = TTConfigForge.biomes.textSize.get();
        TravelersTitlesCommon.CONFIG.biomes.renderShadow = TTConfigForge.biomes.renderShadow.get();
        TravelersTitlesCommon.CONFIG.biomes.textYOffset = TTConfigForge.biomes.textYOffset.get();
        TravelersTitlesCommon.CONFIG.biomes.textXOffset = TTConfigForge.biomes.textXOffset.get();
        TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize = TTConfigForge.biomes.recentBiomeCacheSize.get();
        TravelersTitlesCommon.CONFIG.biomes.centerText = TTConfigForge.biomes.centerText.get();
        TravelersTitlesCommon.CONFIG.biomes.resetBiomeCacheOnDimensionChange = TTConfigForge.biomes.resetBiomeCacheOnDimensionChange.get();
        TravelersTitlesCommon.CONFIG.biomes.onlyUpdateAtSurface = TTConfigForge.biomes.onlyUpdateAtSurface.get();
        TravelersTitlesCommon.CONFIG.biomes.biomeBlacklist = parseList(TTConfigForge.biomes.biomeBlacklist.get(), "Blacklisted Biomes");
        TravelersTitlesCommon.CONFIG.dimensions.enabled = TTConfigForge.dimensions.enabled.get();
        TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime = TTConfigForge.dimensions.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime = TTConfigForge.dimensions.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime = TTConfigForge.dimensions.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.dimensions.textColor = TTConfigForge.dimensions.textColor.get();
        TravelersTitlesCommon.CONFIG.dimensions.textSize = TTConfigForge.dimensions.textSize.get();
        TravelersTitlesCommon.CONFIG.dimensions.renderShadow = TTConfigForge.dimensions.renderShadow.get();
        TravelersTitlesCommon.CONFIG.dimensions.textYOffset = TTConfigForge.dimensions.textYOffset.get();
        TravelersTitlesCommon.CONFIG.dimensions.textXOffset = TTConfigForge.dimensions.textXOffset.get();
        TravelersTitlesCommon.CONFIG.dimensions.centerText = TTConfigForge.dimensions.centerText.get();
        TravelersTitlesCommon.CONFIG.dimensions.onlyUpdateAtSurface = TTConfigForge.dimensions.onlyUpdateAtSurface.get();
        TravelersTitlesCommon.CONFIG.dimensions.dimensionBlacklist = parseList(TTConfigForge.dimensions.dimensionBlacklist.get(), "Blacklisted Dimensions");
        TravelersTitlesCommon.CONFIG.sound.biomeVolume = TTConfigForge.sound.biomeVolume.get();
        TravelersTitlesCommon.CONFIG.sound.biomePitch = TTConfigForge.sound.biomePitch.get();
        TravelersTitlesCommon.CONFIG.sound.dimensionVolume = TTConfigForge.sound.dimensionVolume.get();
        TravelersTitlesCommon.CONFIG.sound.dimensionPitch = TTConfigForge.sound.dimensionPitch.get();
        TravelersTitlesCommon.CONFIG.sound.waystoneVolume = TTConfigForge.sound.waystoneVolume.get();
        TravelersTitlesCommon.CONFIG.sound.waystonePitch = TTConfigForge.sound.waystonePitch.get();
        TravelersTitlesCommon.CONFIG.waystones.enabled = TTConfigForge.waystones.enabled.get();
        TravelersTitlesCommon.CONFIG.waystones.textFadeInTime = TTConfigForge.waystones.textFadeInTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textDisplayTime = TTConfigForge.waystones.textDisplayTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textFadeOutTime = TTConfigForge.waystones.textFadeOutTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textCooldownTime = TTConfigForge.waystones.textCooldownTime.get();
        TravelersTitlesCommon.CONFIG.waystones.textColor = TTConfigForge.waystones.textColor.get();
        TravelersTitlesCommon.CONFIG.waystones.textSize = TTConfigForge.waystones.textSize.get();
        TravelersTitlesCommon.CONFIG.waystones.renderShadow = TTConfigForge.waystones.renderShadow.get();
        TravelersTitlesCommon.CONFIG.waystones.textYOffset = TTConfigForge.waystones.textYOffset.get();
        TravelersTitlesCommon.CONFIG.waystones.textXOffset = TTConfigForge.waystones.textXOffset.get();
        TravelersTitlesCommon.CONFIG.waystones.recentWaystoneCacheSize = TTConfigForge.waystones.recentWaystoneCacheSize.get();
        TravelersTitlesCommon.CONFIG.waystones.centerText = TTConfigForge.waystones.centerText.get();
        TravelersTitlesCommon.CONFIG.waystones.resetWaystoneCacheOnDimensionChange = TTConfigForge.waystones.resetWaystoneCacheOnDimensionChange.get();
        TravelersTitlesCommon.CONFIG.waystones.range = TTConfigForge.waystones.range.get();
        TravelersTitlesCommon.CONFIG.waystones.waystonesOverrideBiomeTitle = TTConfigForge.waystones.waystonesOverrideBiomeTitle.get();
        TravelersTitlesCommon.CONFIG.waystones.onlyUpdateAtSurface = TTConfigForge.waystones.onlyUpdateAtSurface.get();
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
