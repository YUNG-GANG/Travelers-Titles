package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
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

public class TTModClient {
    public static TitleRenderer<Biome> biomeTitleRenderer = new TitleRenderer<>(
        TTConfig.biomes.recentBiomeCacheSize.get(),
        TTConfig.biomes.enabled.get(),
        TTConfig.biomes.textFadeInTime.get(),
        TTConfig.biomes.textDisplayTime.get(),
        TTConfig.biomes.textFadeOutTime.get(),
        TTConfig.biomes.textColor.get(),
        TTConfig.biomes.renderShadow.get(),
        TTConfig.biomes.textSize.get(),
        TTConfig.biomes.textYOffset.get()
    );

    public static TitleRenderer<DimensionType> dimensionTitleRenderer = new TitleRenderer<>(
        1,
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
                // If dimension has surface, make sure player isn't underground
                if (TTConfig.general.onlyUpdateAtSurface.get() && event.player.world.getDimensionType().hasSkyLight() && !event.player.world.canBlockSeeSky(currPos)) {
                    return;
                }

                // Begin display dimension text logic
                DimensionType currDimension = event.player.world.getDimensionType();
                if (dimensionTitleRenderer.enabled && !dimensionTitleRenderer.containsEntry(d -> d == currDimension)) {
                    biomeTitleRenderer.reset(); // Clear biome text when changing dimensions
                    biomeTitleRenderer.recentEntries.clear();

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
                        dimensionTitleRenderer.addRecentEntry(currDimension);
                    }
                }

                // Begin display biome text logic
                Biome currBiome = event.player.world.getBiome(currPos);

                // Get biome key
                ResourceLocation biomeBaseKey = event.player.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(currBiome);
                String overrideBiomeNameKey = Util.makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
                String normalBiomeNameKey = Util.makeTranslationKey("biome", biomeBaseKey);

                if (biomeTitleRenderer.enabled && biomeTitleRenderer.cooldownTimer <= 0 && !biomeTitleRenderer.containsEntry(b -> event.player.world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(b) == biomeBaseKey)) {
                    // Ignore blacklisted biomes
                    if (biomeBaseKey != null && !blacklistedBiomes.contains(biomeBaseKey.toString())) {
                        ITextComponent biomeTitle;

                        // We will only display name if entry for biome found
                        if (LanguageMap.getInstance().func_230506_b_(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
                            biomeTitle = new TranslationTextComponent(overrideBiomeNameKey);
                        } else if (LanguageMap.getInstance().func_230506_b_(normalBiomeNameKey)) { // Next, check for normal biome lang entry
                            biomeTitle = new TranslationTextComponent(normalBiomeNameKey);
                        } else {
                            return; // No entry found
                        }

                        // Get color of text for biome, if entry exists. Otherwise default to normal color
                        String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
                        String normalBiomeColorKey = normalBiomeNameKey + ".color";
                        String biomeColorStr;
                        if (LanguageMap.getInstance().func_230506_b_(overrideBiomeColorKey)) {
                            biomeColorStr = LanguageMap.getInstance().func_230503_a_(overrideBiomeColorKey);
                        } else if (LanguageMap.getInstance().func_230506_b_(normalBiomeColorKey)) {
                            biomeColorStr = LanguageMap.getInstance().func_230503_a_(normalBiomeColorKey);
                        } else {
                            biomeColorStr = biomeTitleRenderer.titleDefaultTextColor;
                        }

                        // No need to display if title hasn't changed
                        if (biomeTitleRenderer.displayedTitle != null && biomeTitle.getString().equals(biomeTitleRenderer.displayedTitle.getString())) {
                            return;
                        }

                        // Set display
                        biomeTitleRenderer.setColor(biomeColorStr);
                        biomeTitleRenderer.displayTitle(biomeTitle, null);
                        biomeTitleRenderer.cooldownTimer = TTConfig.biomes.textCooldownTime.get();
                        biomeTitleRenderer.addRecentEntry(currBiome);
                    }
                }
            }
        }
    }

    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isGamePaused()) {
                biomeTitleRenderer.tick();
                dimensionTitleRenderer.tick();
            }
        }
    }

    public static void renderTitles(RenderGameOverlayEvent.Pre event) {
        biomeTitleRenderer.renderText(event);
        dimensionTitleRenderer.renderText(event);
    }
}
