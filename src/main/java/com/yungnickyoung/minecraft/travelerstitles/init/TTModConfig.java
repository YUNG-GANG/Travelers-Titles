package com.yungnickyoung.minecraft.travelerstitles.init;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
//import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class TTModConfig {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TTConfig.SPEC, "travelerstitles-forge-1_18.toml");
        MinecraftForge.EVENT_BUS.addListener(TTModConfig::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TTModConfig::configChanged);
    }

    public static void onWorldLoad(WorldEvent.Load event) {
        reloadConfig();
    }

    public static void configChanged(ModConfigEvent event) {
        ModConfig config = event.getConfig();

        // Bake config
        if (config.getSpec() == TTConfig.SPEC) {
            reloadConfig();
        }
    }

    /**
     * Bakes in updated config values.
     */
    public static void reloadConfig() {
        // Biome
        TravelersTitles.titleManager.biomeTitleRenderer.maxRecentListSize = TTConfig.biomes.recentBiomeCacheSize.get();
        TravelersTitles.titleManager.biomeTitleRenderer.enabled = TTConfig.biomes.enabled.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeInTicks = TTConfig.biomes.textFadeInTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleDisplayTime = TTConfig.biomes.textDisplayTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeOutTicks = TTConfig.biomes.textFadeOutTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleDefaultTextColor = TTConfig.biomes.textColor.get();
        TravelersTitles.titleManager.biomeTitleRenderer.showTextShadow = TTConfig.biomes.renderShadow.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleTextSize = TTConfig.biomes.textSize.get().floatValue();
        TravelersTitles.titleManager.biomeTitleRenderer.titleXOffset = TTConfig.biomes.textXOffset.get().floatValue();
        TravelersTitles.titleManager.biomeTitleRenderer.titleYOffset = TTConfig.biomes.textYOffset.get().floatValue();
        TravelersTitles.titleManager.biomeTitleRenderer.isTextCentered = TTConfig.biomes.centerText.get();

        // Dimension
        TravelersTitles.titleManager.dimensionTitleRenderer.enabled = TTConfig.dimensions.enabled.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeInTicks = TTConfig.dimensions.textFadeInTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDisplayTime = TTConfig.dimensions.textDisplayTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeOutTicks = TTConfig.dimensions.textFadeOutTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor = TTConfig.dimensions.textColor.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.showTextShadow = TTConfig.dimensions.renderShadow.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleTextSize = TTConfig.dimensions.textSize.get().floatValue();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleXOffset = TTConfig.dimensions.textXOffset.get().floatValue();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleYOffset = TTConfig.dimensions.textYOffset.get().floatValue();
        TravelersTitles.titleManager.dimensionTitleRenderer.isTextCentered = TTConfig.dimensions.centerText.get();

        // Waystones
//        WaystonesCompat.updateRendererFromConfig(TTConfig.waystones);

        // Parse & save biome blacklist
        String rawStringofList = TTConfig.biomes.biomeBlacklist.get();
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
        rawStringofList = TTConfig.dimensions.dimensionBlacklist.get();
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
