package com.yungnickyoung.minecraft.travelerstitles.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModule;
import com.yungnickyoung.minecraft.travelerstitles.module.TagModule;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;

public class TitleRenderManager {
    public final TitleRenderer<Biome> biomeTitleRenderer = new TitleRenderer<>(
        TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize,
        TravelersTitlesCommon.CONFIG.biomes.enabled,
        TravelersTitlesCommon.CONFIG.biomes.textFadeInTime,
        TravelersTitlesCommon.CONFIG.biomes.textDisplayTime,
        TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime,
        TravelersTitlesCommon.CONFIG.biomes.textColor,
        TravelersTitlesCommon.CONFIG.biomes.renderShadow,
        TravelersTitlesCommon.CONFIG.biomes.textSize,
        TravelersTitlesCommon.CONFIG.biomes.textXOffset,
        TravelersTitlesCommon.CONFIG.biomes.textYOffset,
        TravelersTitlesCommon.CONFIG.biomes.centerText
    );

    public final TitleRenderer<DimensionType> dimensionTitleRenderer = new TitleRenderer<>(
        1,
        TravelersTitlesCommon.CONFIG.dimensions.enabled,
        TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime,
        TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime,
        TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime,
        TravelersTitlesCommon.CONFIG.dimensions.textColor,
        TravelersTitlesCommon.CONFIG.dimensions.renderShadow,
        TravelersTitlesCommon.CONFIG.dimensions.textSize,
        TravelersTitlesCommon.CONFIG.dimensions.textXOffset,
        TravelersTitlesCommon.CONFIG.dimensions.textYOffset,
        TravelersTitlesCommon.CONFIG.dimensions.centerText
    );

    /**
     * Ticks all renderers.
     * Basically just decrements all renderers.
     */
    public void clientTick() {
        if (!Minecraft.getInstance().isPaused()) {
            dimensionTitleRenderer.tick();
            Services.WAYSTONES.clientTick();
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
            Services.WAYSTONES.renderText(partialTicks, poseStack);
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

            // Render dimension title
            updateDimensionTitle(world, player, isPlayerUnderground);

            // Render waystone title
            boolean isRenderingWaystoneTitle = updateWaystoneTitle(player, isPlayerUnderground);

            // Render biome title if title renderer is not being used by waystone title
            if (!TravelersTitlesCommon.CONFIG.waystones.waystonesOverrideBiomeTitle || !isRenderingWaystoneTitle) {
                updateBiomeTitle(world, playerPos, player, isPlayerUnderground);
            } else {
                biomeTitleRenderer.clearTimer();
            }
        }
    }

    public void playerChangedDimension(Object entity) {
        if (entity instanceof Player) {
            // Reset biome cache on dimension change, if enabled
            if (TravelersTitlesCommon.CONFIG.biomes.enabled && TravelersTitlesCommon.CONFIG.biomes.resetBiomeCacheOnDimensionChange) {
                biomeTitleRenderer.clearTimer();
                biomeTitleRenderer.recentEntries.clear();
                biomeTitleRenderer.displayedTitle = null;
            }

            // Reset waystones cache on dimension change, if enabled
            if (CompatModule.isWaystonesLoaded && TravelersTitlesCommon.CONFIG.waystones.enabled && TravelersTitlesCommon.CONFIG.waystones.resetWaystoneCacheOnDimensionChange) {
                Services.WAYSTONES.reset();
            }
        }
    }

    /**
     * Updates the dimension title, color, and render timers if conditions are met.
     */
    private void updateDimensionTitle(Level world, Player player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TravelersTitlesCommon.CONFIG.dimensions.onlyUpdateAtSurface) {
            return;
        }

        DimensionType currDimension = world.dimensionType();

        if (dimensionTitleRenderer.enabled && !dimensionTitleRenderer.matchesAnyRecentEntry(d -> d == currDimension)) {
            // Get dimension key
            ResourceLocation dimensionBaseKey = world.dimension().location();
            String dimensionNameKey = Util.makeDescriptionId(TravelersTitlesCommon.MOD_ID, dimensionBaseKey);

            // Ignore blacklisted dimensions
            if (!TravelersTitlesCommon.CONFIG.dimensions.dimensionBlacklist.contains(dimensionBaseKey.toString())) {
                Component dimensionTitle;
                dimensionTitle = Language.getInstance().has(dimensionNameKey)
                    ? Component.translatable(dimensionNameKey)
                    : Component.literal("???"); // Display ??? for unknown dimensions

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
                player.playSound(SoundModule.DIMENSION.get(), (float) TravelersTitlesCommon.CONFIG.sound.dimensionVolume, (float) TravelersTitlesCommon.CONFIG.sound.dimensionPitch);
            }
        }
    }

    /**
     * Updates the biome title, color, and render timers if conditions are met.
     */
    private void updateBiomeTitle(Level world, BlockPos playerPos, Player player, boolean isPlayerUnderground) {
        Holder<Biome> biomeHolder = world.getBiome(playerPos);

        boolean isUndergroundBiome = biomeHolder.is(Biomes.LUSH_CAVES) || biomeHolder.is(Biomes.DRIPSTONE_CAVES) || biomeHolder.is(TagModule.IS_UNDERGROUND);
        if (isPlayerUnderground && TravelersTitlesCommon.CONFIG.biomes.onlyUpdateAtSurface && !isUndergroundBiome) {
            return;
        }

        ResourceLocation biomeBaseKey = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biomeHolder.value());

        if (
            biomeTitleRenderer.enabled &&
            biomeTitleRenderer.cooldownTimer <= 0 &&
            !biomeTitleRenderer.matchesAnyRecentEntry(b -> world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(b) == biomeBaseKey)
        ) {
            String overrideBiomeNameKey = Util.makeDescriptionId(TravelersTitlesCommon.MOD_ID + ".biome", biomeBaseKey);
            String normalBiomeNameKey = Util.makeDescriptionId("biome", biomeBaseKey);

            // Ignore blacklisted biomes
            if (biomeBaseKey != null && !TravelersTitlesCommon.CONFIG.biomes.biomeBlacklist.contains(biomeBaseKey.toString())) {
                Component biomeTitle;

                // We will only display name if entry for biome found
                if (Language.getInstance().has(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
                    biomeTitle = Component.translatable(overrideBiomeNameKey);
                } else if (Language.getInstance().has(normalBiomeNameKey)) { // Next, check for normal biome lang entry
                    biomeTitle = Component.translatable(normalBiomeNameKey);
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
                biomeTitleRenderer.cooldownTimer = TravelersTitlesCommon.CONFIG.biomes.textCooldownTime;
                biomeTitleRenderer.addRecentEntry(biomeHolder.value());

                // Play biome entry sound if we haven't just changed dimensions or entered a waystone's range.
                // This ensures the biome sound doesn't overlap with the dimension and waystone sounds.
                if (dimensionTitleRenderer.titleTimer <= 0) {
                    if (!CompatModule.isWaystonesLoaded || !Services.WAYSTONES.isRendering()) {
                        player.playSound(SoundModule.BIOME.get(), (float) TravelersTitlesCommon.CONFIG.sound.biomeVolume, (float) TravelersTitlesCommon.CONFIG.sound.biomePitch);
                    }
                }
            }
        }
    }

    /**
     * Updates the waystone title, color, and render timers if conditions are met.
     *
     * @return true if waystone title is currently being displayed
     */
    private boolean updateWaystoneTitle(Player player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TravelersTitlesCommon.CONFIG.waystones.onlyUpdateAtSurface) {
            return false;
        }

        if (CompatModule.isWaystonesLoaded && TravelersTitlesCommon.CONFIG.waystones.enabled) {
            return Services.WAYSTONES.updateWaystoneTitle(player);
        }

        return false;
    }
}
