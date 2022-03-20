package com.yungnickyoung.minecraft.travelerstitles.module;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;

import java.util.ArrayList;
import java.util.List;

public class ConfigModule {
    public final Biomes biomes = new Biomes();
    public final Dimensions dimensions = new Dimensions();
    public final Sound sound = new Sound();
    public final Waystones waystones = new Waystones();

    public static class Biomes {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 50;
        public int textFadeOutTime = 10;
        public int textCooldownTime = 80;
        public String textColor = "ffffff";
        public double textSize = 2.1;
        public boolean renderShadow = true;
        public double textYOffset = -33.0;
        public double textXOffset = 0.0;
        public List<String> biomeBlacklist = Lists.newArrayList("minecraft:the_end", "minecraft:river", "minecraft:frozen_river");
        public int recentBiomeCacheSize = 5;
        public boolean centerText = true;
        public boolean resetBiomeCacheOnDimensionChange = true;
        public boolean onlyUpdateAtSurface = true;
    }

    public static class Dimensions {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 70;
        public int textFadeOutTime = 20;
        public String textColor = "ffffff";
        public double textSize = 3.0;
        public boolean renderShadow = true;
        public double textYOffset = -32.0;
        public double textXOffset = 0.0;
        public List<String> dimensionBlacklist = new ArrayList<>();
        public boolean centerText = true;
        public boolean onlyUpdateAtSurface = false;
    }

    public static class Sound {
        public double biomeVolume = 1.0;
        public double biomePitch = 1.0;
        public double dimensionVolume = 1.0;
        public double dimensionPitch = 1.0;
        public double waystoneVolume = 1.0;
        public double waystonePitch = 1.0;
    }

    public static class Waystones {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 50;
        public int textFadeOutTime = 10;
        public int textCooldownTime = 80;
        public String textColor = "c2b740";
        public double textSize = 2.1;
        public boolean renderShadow = true;
        public double textYOffset = -33.0;
        public double textXOffset = 0.0;
        public int recentWaystoneCacheSize = 3;
        public boolean centerText = true;
        public boolean resetWaystoneCacheOnDimensionChange = true;
        public int range = 30;
        public boolean waystonesOverrideBiomeTitle = true;
        public boolean onlyUpdateAtSurface = false;
    }

    /**
     * Bakes in updated config values.
     */
    public static void updateRenderersFromConfig() {
        // Biome
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.maxRecentListSize = TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.enabled = TravelersTitlesCommon.CONFIG.biomes.enabled;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleFadeInTicks = TravelersTitlesCommon.CONFIG.biomes.textFadeInTime;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleDisplayTime = TravelersTitlesCommon.CONFIG.biomes.textDisplayTime;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleFadeOutTicks = TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleDefaultTextColor = TravelersTitlesCommon.CONFIG.biomes.textColor;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.showTextShadow = TravelersTitlesCommon.CONFIG.biomes.renderShadow;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleTextSize = (float) TravelersTitlesCommon.CONFIG.biomes.textSize;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleXOffset = (float) TravelersTitlesCommon.CONFIG.biomes.textXOffset;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleYOffset = (float) TravelersTitlesCommon.CONFIG.biomes.textYOffset;
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.isTextCentered = TravelersTitlesCommon.CONFIG.biomes.centerText;

        // Dimension
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.enabled = TravelersTitlesCommon.CONFIG.dimensions.enabled;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleFadeInTicks = TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleDisplayTime = TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleFadeOutTicks = TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleDefaultTextColor = TravelersTitlesCommon.CONFIG.dimensions.textColor;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.showTextShadow = TravelersTitlesCommon.CONFIG.dimensions.renderShadow;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleTextSize = (float) TravelersTitlesCommon.CONFIG.dimensions.textSize;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleXOffset = (float) TravelersTitlesCommon.CONFIG.dimensions.textXOffset;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleYOffset = (float) TravelersTitlesCommon.CONFIG.dimensions.textYOffset;
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.isTextCentered = TravelersTitlesCommon.CONFIG.dimensions.centerText;

        // Waystones
        Services.WAYSTONES.updateRendererFromConfig(TravelersTitlesCommon.CONFIG.waystones);
    }
}
