package com.yungnickyoung.minecraft.travelerstitles.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class TitleRenderManager {
    public final TitleRenderer<Biome> biomeTitleRenderer = new TitleRenderer<>(
        TTConfig.biomes.recentBiomeCacheSize.get(),
        TTConfig.biomes.enabled.get(),
        TTConfig.biomes.textFadeInTime.get(),
        TTConfig.biomes.textDisplayTime.get(),
        TTConfig.biomes.textFadeOutTime.get(),
        TTConfig.biomes.textColor.get(),
        TTConfig.biomes.renderShadow.get(),
        TTConfig.biomes.textSize.get(),
        TTConfig.biomes.textXOffset.get(),
        TTConfig.biomes.textYOffset.get(),
        TTConfig.biomes.centerText.get()
    );

    public final TitleRenderer<DimensionType> dimensionTitleRenderer = new TitleRenderer<>(
        1,
        TTConfig.dimensions.enabled.get(),
        TTConfig.dimensions.textFadeInTime.get(),
        TTConfig.dimensions.textDisplayTime.get(),
        TTConfig.dimensions.textFadeOutTime.get(),
        TTConfig.dimensions.textColor.get(),
        TTConfig.dimensions.renderShadow.get(),
        TTConfig.dimensions.textSize.get(),
        TTConfig.dimensions.textXOffset.get(),
        TTConfig.dimensions.textYOffset.get(),
        TTConfig.dimensions.centerText.get()
    );

    public List<String> blacklistedBiomes = new ArrayList<>();
    public List<String> blacklistedDimensions = new ArrayList<>();

    /**
     * Ticks all renderers.
     */
    public void clientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isGamePaused()) {
                dimensionTitleRenderer.tick();
                WaystonesCompat.clientTick();
                biomeTitleRenderer.tick();
            }
        }
    }

    /**
     * Renders all titles.
     */
    public void renderTitles(final RenderGameOverlayEvent.Pre event) {
        if (!Minecraft.getInstance().gameSettings.showDebugInfo && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            float partialTicks = event.getPartialTicks();
            MatrixStack matrixStack = event.getMatrixStack();

            // Render titles
            dimensionTitleRenderer.renderText(partialTicks, matrixStack);
            WaystonesCompat.renderText(partialTicks, matrixStack);
            biomeTitleRenderer.renderText(partialTicks, matrixStack);
        }
    }

    /**
     * Initializes rendering titles if conditions are met (e.g. player changed biome or dimension)
     */
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        BlockPos playerPos = event.player.getPosition();
        World world = player.world;

        if (player instanceof ClientPlayerEntity && world != null && world.isBlockPresent(playerPos)) {
            boolean isPlayerUnderground = world.getDimensionType().hasSkyLight() && !world.canBlockSeeSky(playerPos);

            // Render dimension title
            updateDimensionTitle(world, player, isPlayerUnderground);

            // Render waystone title
            boolean isRenderingWaystoneTitle = updateWaystoneTitle(player, isPlayerUnderground);

            // Render biome title if title renderer is not being used by waystone title
            if (!TTConfig.waystones.waystonesOverrideBiomeTitle.get() || !isRenderingWaystoneTitle) {
                updateBiomeTitle(world, playerPos, player, isPlayerUnderground);
            } else {
                biomeTitleRenderer.clearTimer();
            }
        }
    }

    public void playerChangedDimension() {
        // Reset biome cache on dimension change
        if (
            TTConfig.biomes.enabled.get() &&
            TTConfig.biomes.resetBiomeCacheOnDimensionChange.get()
        ) {
            biomeTitleRenderer.clearTimer();
            biomeTitleRenderer.recentEntries.clear();
        }

        // Reset waystones cache on dimension change
        if (
            ModList.get().isLoaded("waystones") &&
            TTConfig.waystones.enabled.get() &&
            TTConfig.waystones.resetWaystoneCacheOnDimensionChange.get()
        ) {
            WaystonesCompat.reset();
        }
    }

    /**
     * Updates the dimension title, color, and render timers if conditions are met.
     */
    private void updateDimensionTitle(World world, PlayerEntity player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.dimensions.onlyUpdateAtSurface.get()) {
            return;
        }

        DimensionType currDimension = world.getDimensionType();

        if (dimensionTitleRenderer.enabled && !dimensionTitleRenderer.containsEntry(d -> d == currDimension)) {
            // Get dimension key
            ResourceLocation dimensionBaseKey = world.getDimensionKey().getLocation();
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

                // Play dimension entry sound
                player.playSound(TTModSound.DIMENSION, TTConfig.sound.dimensionVolume.get().floatValue(), TTConfig.sound.dimensionPitch.get().floatValue());
            }
        }
    }

    /**
     * Updates the biome title, color, and render timers if conditions are met.
     */
    private void updateBiomeTitle(World world, BlockPos playerPos, PlayerEntity player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.biomes.onlyUpdateAtSurface.get()) {
            return;
        }

        Biome currBiome = world.getBiome(playerPos);
        ResourceLocation biomeBaseKey = world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(currBiome);

        if (
            biomeTitleRenderer.enabled &&
            biomeTitleRenderer.cooldownTimer <= 0 &&
            !biomeTitleRenderer.containsEntry(b -> world.func_241828_r().getRegistry(Registry.BIOME_KEY).getKey(b) == biomeBaseKey)
        ) {
            String overrideBiomeNameKey = Util.makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
            String normalBiomeNameKey = Util.makeTranslationKey("biome", biomeBaseKey);

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

                // Play biome entry sound
                player.playSound(TTModSound.BIOME, TTConfig.sound.biomeVolume.get().floatValue(), TTConfig.sound.biomePitch.get().floatValue());
            }
        }
    }

    /**
     * Updates the waystone title, color, and render timers if conditions are met.
     *
     * @return true if waystone title is currently being displayed
     */
    private boolean updateWaystoneTitle(PlayerEntity player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.waystones.onlyUpdateAtSurface.get()) {
            return false;
        }

        if (ModList.get().isLoaded("waystones") && TTConfig.waystones.enabled.get()) {
            return WaystonesCompat.updateWaystoneTitle(player);
        }

        return false;
    }
}
