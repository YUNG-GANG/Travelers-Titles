package com.yungnickyoung.minecraft.travelerstitles.init;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;

public class TTModConfig {
    public static void init() {
        // Register mod config with Forge
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, TTConfig.SPEC, "travelerstitles-forge-1_16.toml");
        MinecraftForge.EVENT_BUS.addListener(TTModConfig::onWorldLoad);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(TTModConfig::configChanged);
    }

    public static void onWorldLoad(WorldEvent.Load event) {
        reloadConfig();
    }

    public static void configChanged(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();

        // Bake config
        if (config.getSpec() == TTConfig.SPEC) {
            reloadConfig();
        }
    }

    /**
     * Bakes in updated config values.
     */
    private static void reloadConfig() {
        // Biome
        TTModClient.biomeTitleRenderer.enabled = TTConfig.biomes.enabled.get();
        TTModClient.biomeTitleRenderer.titleFadeInTicks = TTConfig.biomes.textFadeInTime.get();
        TTModClient.biomeTitleRenderer.titleDisplayTime = TTConfig.biomes.textDisplayTime.get();
        TTModClient.biomeTitleRenderer.titleFadeOutTicks = TTConfig.biomes.textFadeOutTime.get();
        TTModClient.biomeTitleRenderer.titleDefaultTextColor = TTConfig.biomes.textColor.get();
        TTModClient.biomeTitleRenderer.showTextShadow = TTConfig.biomes.renderShadow.get();
        TTModClient.biomeTitleRenderer.titleTextSize = TTConfig.biomes.textSize.get().floatValue();
        TTModClient.biomeTitleRenderer.titleYOffset = TTConfig.biomes.textYOffset.get().floatValue();

        // Dimension
        TTModClient.dimensionTitleRenderer.enabled = TTConfig.dimensions.enabled.get();
        TTModClient.dimensionTitleRenderer.titleFadeInTicks = TTConfig.dimensions.textFadeInTime.get();
        TTModClient.dimensionTitleRenderer.titleDisplayTime = TTConfig.dimensions.textDisplayTime.get();
        TTModClient.dimensionTitleRenderer.titleFadeOutTicks = TTConfig.dimensions.textFadeOutTime.get();
        TTModClient.dimensionTitleRenderer.titleDefaultTextColor = TTConfig.dimensions.textColor.get();
        TTModClient.dimensionTitleRenderer.showTextShadow = TTConfig.dimensions.renderShadow.get();
        TTModClient.dimensionTitleRenderer.titleTextSize = TTConfig.dimensions.textSize.get().floatValue();
        TTModClient.dimensionTitleRenderer.titleYOffset = TTConfig.dimensions.textYOffset.get().floatValue();

        // Parse & save biome blacklist
        String rawStringofList = TTConfig.biomes.biomeBlacklist.get();
        int strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using empty list instead...");
            TTModClient.blacklistedBiomes = new ArrayList<>();
            return;
        }

        // Parse string to list
        List<String> inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TTModClient.blacklistedBiomes = Lists.newArrayList(inputListOfStrings);

        // Parse & save dimension blacklist
        rawStringofList = TTConfig.dimensions.dimensionBlacklist.get();
        strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Dimensions'. Using empty list instead...");
            TTModClient.blacklistedDimensions = new ArrayList<>();
            return;
        }

        // Parse string to list
        inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TTModClient.blacklistedDimensions = Lists.newArrayList(inputListOfStrings);
    }
}
