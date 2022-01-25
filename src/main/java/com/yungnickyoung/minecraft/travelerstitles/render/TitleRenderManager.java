package com.yungnickyoung.minecraft.travelerstitles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;

public class TitleRenderManager {
    public final TitleRenderer<Biome> biomeTitleRenderer = new TitleRenderer<>();

    public final TitleRenderer<DimensionType> dimensionTitleRenderer = new TitleRenderer<>();

    public List<String> blacklistedBiomes = new ArrayList<>();
    public List<String> blacklistedDimensions = new ArrayList<>();

    /**
     * Ticks all renderers.
     * Basically just decrements all renderers.
     */
    public void clientTick() {
        if (!Minecraft.getInstance().isPaused()) {
            dimensionTitleRenderer.tick();
            biomeTitleRenderer.tick();
        }
    }

    /**
     * Renders all titles that are marked as ready to render.
     */
    public void renderTitles(PoseStack poseStack, float partialTicks) {
        if (!Minecraft.getInstance().options.renderDebug) {
            dimensionTitleRenderer.renderText(partialTicks, poseStack);
            biomeTitleRenderer.renderText(partialTicks, poseStack);
        }
    }

    /**
     * Marks titles for rendering if conditions are met (e.g. player changed biome or dimension)
     */
    public void playerTick(Player player) {
        if (player instanceof LocalPlayer && player.level.isLoaded(player.blockPosition())) {
            BlockPos playerPos = player.blockPosition();
            Level world = player.level;
            boolean isPlayerUnderground = world.dimensionType().hasSkyLight() && !world.canSeeSky(playerPos);

            updateDimensionTitle(world, player, isPlayerUnderground);
            updateBiomeTitle(world, playerPos, player, isPlayerUnderground);
        }
    }

    public void playerChangedDimension(Object entity) {
        if (entity instanceof Player) {
            // Reset biome cache on dimension change, if enabled
            if (TravelersTitles.CONFIG.travelersTitles.biomes.enabled && TravelersTitles.CONFIG.travelersTitles.biomes.resetBiomeCacheOnDimensionChange) {
                biomeTitleRenderer.clearTimer();
                biomeTitleRenderer.recentEntries.clear();
                biomeTitleRenderer.displayedTitle = null;
            }
        }
    }

    /**
     * Updates the dimension title, color, and render timers if conditions are met.
     */
    private void updateDimensionTitle(Level world, Player player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TravelersTitles.CONFIG.travelersTitles.dimensions.onlyUpdateAtSurface) {
            return;
        }

        DimensionType currDimension = world.dimensionType();

        if (dimensionTitleRenderer.enabled && !dimensionTitleRenderer.matchesAnyRecentEntry(d -> d == currDimension)) {
            // Get dimension key
            ResourceLocation dimensionBaseKey = world.dimension().location();
            String dimensionNameKey = Util.makeDescriptionId(TravelersTitles.MOD_ID, dimensionBaseKey);

            // Ignore blacklisted dimensions
            if (!blacklistedDimensions.contains(dimensionBaseKey.toString())) {
                Component dimensionTitle;
                dimensionTitle = Language.getInstance().has(dimensionNameKey)
                    ? new TranslatableComponent(dimensionNameKey)
                    : new TextComponent("???"); // Display ??? for unknown dimensions

                // Get color of text for dimension, if entry exists. Otherwise default to normal color
                String dimensionColorKey = dimensionNameKey + ".color";
                String dimensionColorStr = Language.getInstance().has(dimensionColorKey)
                    ? Language.getInstance().getOrDefault(dimensionColorKey)
                    : dimensionTitleRenderer.titleDefaultTextColor;

                // Set display
                dimensionTitleRenderer.setColor(dimensionColorStr);
                dimensionTitleRenderer.displayTitle(dimensionTitle, null);
                dimensionTitleRenderer.addRecentEntry(currDimension);

                // Play dimension entry sound
                player.playSound(TTModSound.DIMENSION, TravelersTitles.CONFIG.travelersTitles.sound.dimensionVolume, TravelersTitles.CONFIG.travelersTitles.sound.dimensionPitch);
            }
        }
    }

    /**
     * Updates the biome title, color, and render timers if conditions are met.
     */
    private void updateBiomeTitle(Level world, BlockPos playerPos, Player player, boolean isPlayerUnderground) {
        Biome currBiome = world.getBiome(playerPos);

        if (isPlayerUnderground && TravelersTitles.CONFIG.travelersTitles.biomes.onlyUpdateAtSurface && currBiome.getBiomeCategory() != Biome.BiomeCategory.UNDERGROUND) {
            return;
        }

        ResourceLocation biomeBaseKey = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(currBiome);

        if (
            biomeTitleRenderer.enabled &&
            biomeTitleRenderer.cooldownTimer <= 0 &&
            !biomeTitleRenderer.matchesAnyRecentEntry(b -> world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(b) == biomeBaseKey)
        ) {
            String overrideBiomeNameKey = Util.makeDescriptionId(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
            String normalBiomeNameKey = Util.makeDescriptionId("biome", biomeBaseKey);

            // Ignore blacklisted biomes
            if (biomeBaseKey != null && !blacklistedBiomes.contains(biomeBaseKey.toString())) {
                Component biomeTitle;

                // We will only display name if entry for biome found
                if (Language.getInstance().has(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
                    biomeTitle = new TranslatableComponent(overrideBiomeNameKey);
                } else if (Language.getInstance().has(normalBiomeNameKey)) { // Next, check for normal biome lang entry
                    biomeTitle = new TranslatableComponent(normalBiomeNameKey);
                } else {
                    return; // No entry found
                }

                // Get color of text for biome, if entry exists. Otherwise default to normal color
                String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
                String normalBiomeColorKey = normalBiomeNameKey + ".color";
                String biomeColorStr;
                if (Language.getInstance().has(overrideBiomeColorKey)) {
                    biomeColorStr = Language.getInstance().getOrDefault(overrideBiomeColorKey);
                } else if (Language.getInstance().has(normalBiomeColorKey)) {
                    biomeColorStr = Language.getInstance().getOrDefault(normalBiomeColorKey);
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
                biomeTitleRenderer.cooldownTimer = TravelersTitles.CONFIG.travelersTitles.biomes.textCooldownTime;
                biomeTitleRenderer.addRecentEntry(currBiome);

                // Play biome entry sound if we haven't just changed dimensions.
                // This ensures the biome sound doesn't overlap with the dimension sound.
                if (dimensionTitleRenderer.titleTimer <= 0) {
                    player.playSound(TTModSound.BIOME, TravelersTitles.CONFIG.travelersTitles.sound.biomeVolume, TravelersTitles.CONFIG.travelersTitles.sound.biomePitch);
                }
            }
        }
    }
}
