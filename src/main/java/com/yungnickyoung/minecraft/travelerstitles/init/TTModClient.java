package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TTModClient {
    public static TitleRenderer biomeTitleRenderer = new TitleRenderer(
        TTConfig.biomes.enabled.get(),
        TTConfig.biomes.textFadeInTime.get(),
        TTConfig.biomes.textDisplayTime.get(),
        TTConfig.biomes.textFadeOutTime.get(),
        TTConfig.biomes.textColor.get(),
        TTConfig.biomes.renderShadow.get(),
        TTConfig.biomes.textSize.get(),
        TTConfig.biomes.textYOffset.get()
    );

    public static TitleRenderer dimensionTitleRenderer = new TitleRenderer(
        TTConfig.dimensions.enabled.get(),
        TTConfig.dimensions.textFadeInTime.get(),
        TTConfig.dimensions.textDisplayTime.get(),
        TTConfig.dimensions.textFadeOutTime.get(),
        TTConfig.dimensions.textColor.get(),
        TTConfig.dimensions.renderShadow.get(),
        TTConfig.dimensions.textSize.get(),
        TTConfig.dimensions.textYOffset.get()
    );

    public static List<String> blacklistedBiomes = new ArrayList<>();
    public static List<String> blacklistedDimensions = new ArrayList<>();

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(TTModClient::showTextOnBiomeOrDimensionChange);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::clientTick);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::renderTitles);
    }

    public static void showTextOnBiomeOrDimensionChange(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ClientPlayerEntity && event.player.world != null) {
            BlockPos currPos = event.player.getPosition();

            if (event.player.world.isBlockPresent(currPos)) {
                // Display dimension text
                DimensionType currDimension = event.player.world.getDimensionType();
                if (dimensionTitleRenderer.enabled && dimensionTitleRenderer.prevDimension != currDimension) {
                    dimensionTitleRenderer.prevDimension = currDimension;
                    biomeTitleRenderer.reset(); // Clear biome text when changing dimensions

                    // Get dimension key
                    ResourceLocation dimensionBaseKey = event.player.world.getDimensionKey().getLocation();
                    String dimensionNameKey = Util.makeTranslationKey(TravelersTitles.MOD_ID, dimensionBaseKey);

                    // Ignore blacklisted dimensions
                    if (!blacklistedDimensions.contains(dimensionBaseKey.toString())) {
                        ITextComponent dimensionTitle;
                        dimensionTitle = LanguageMap.getInstance().func_230506_b_(dimensionNameKey)
                            ? new TranslationTextComponent(dimensionNameKey)
                            : new StringTextComponent("???"); // Display ??? for unknown dimensions;

                        // Get color of text for dimension, if entry exists. Otherwise default to normal color
                        String dimensionColorKey = dimensionNameKey + ".color";
                        String dimensionColorStr = LanguageMap.getInstance().func_230506_b_(dimensionColorKey)
                            ? LanguageMap.getInstance().func_230503_a_(dimensionColorKey)
                            : dimensionTitleRenderer.titleDefaultTextColor;

                        // Set display
                        dimensionTitleRenderer.setColor(dimensionColorStr);
                        dimensionTitleRenderer.displayTitle(dimensionTitle, null);
                    }
                }

                // Display biome text if dimension is not currently being displayed
                Biome currBiome = event.player.world.getBiome(currPos);
                if (biomeTitleRenderer.enabled && !dimensionTitleRenderer.isDisplaying && (biomeTitleRenderer.prevBiome == null || !Objects.equals(currBiome.getRegistryName(), biomeTitleRenderer.prevBiome.getRegistryName()))) {
                    biomeTitleRenderer.prevBiome = currBiome;

                    // Get biome key
                    ResourceLocation biomeBaseKey = currBiome.getRegistryName();
                    String biomeNameKey = Util.makeTranslationKey("biome", biomeBaseKey);

                    // Ignore blacklisted biomes
                    if (!blacklistedBiomes.contains(currBiome.getRegistryName().toString())) {
                        // Only display name if entry for biome found
                        if (LanguageMap.getInstance().func_230506_b_(biomeNameKey)) {
                            ITextComponent biomeTitle = new TranslationTextComponent(biomeNameKey);

                            // Get color of text for biome, if entry exists. Otherwise default to normal color
                            String biomeColorKey = biomeNameKey + ".color";
                            String biomeColorStr = LanguageMap.getInstance().func_230506_b_(biomeColorKey)
                                ? LanguageMap.getInstance().func_230503_a_(biomeColorKey)
                                : biomeTitleRenderer.titleDefaultTextColor;

                            // Set display
                            biomeTitleRenderer.setColor(biomeColorStr);
                            biomeTitleRenderer.displayTitle(biomeTitle, null);
                        }
                    }
                }
            }
        }
    }

    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isGamePaused()) {
            if (event.phase == TickEvent.Phase.START) {
                if (biomeTitleRenderer.titleTimer > 0) {
                    --biomeTitleRenderer.titleTimer;
                    if (biomeTitleRenderer.titleTimer <= 0) {
                        biomeTitleRenderer.reset();
                    }
                }
                if (dimensionTitleRenderer.titleTimer > 0) {
                    --dimensionTitleRenderer.titleTimer;
                    if (dimensionTitleRenderer.titleTimer <= 0) {
                        dimensionTitleRenderer.reset();
                    }
                }
            }
        }
    }

    public static void renderTitles(RenderGameOverlayEvent.Pre event) {
        biomeTitleRenderer.renderText(event);
        dimensionTitleRenderer.renderText(event);
    }
}
