package com.yungnickyoung.minecraft.travelerstitles.compat;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yungnickyoung.minecraft.travelerstitles.config.ConfigWaystones;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.api.KnownWaystonesEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import java.util.ArrayList;
import java.util.List;

public class WaystonesCompat {
    private static List<IWaystone> knownWaystones = new ArrayList<>();
    private static IWaystone closestWaystone;
    private static int waystoneUpdateTimer = 0;
    private static final TitleRenderer<IWaystone> waystoneTitleRenderer = new TitleRenderer<>(
        TTConfig.waystones.recentWaystoneCacheSize.get(),
        TTConfig.waystones.enabled.get(),
        TTConfig.waystones.textFadeInTime.get(),
        TTConfig.waystones.textDisplayTime.get(),
        TTConfig.waystones.textFadeOutTime.get(),
        TTConfig.waystones.textColor.get(),
        TTConfig.waystones.renderShadow.get(),
        TTConfig.waystones.textSize.get(),
        TTConfig.waystones.textXOffset.get(),
        TTConfig.waystones.textYOffset.get(),
        TTConfig.waystones.centerText.get()
    );

    /**
     * Initializes Waystones compat module.
     * Should only be called if the waystones mod is loaded.
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(WaystonesCompat::updateKnownWaystones);
        MinecraftForge.EVENT_BUS.addListener(WaystonesCompat::updateClosestWaystone);
    }

    /**
     * Updates the stored player's list of known waystones.
     */
    private static void updateKnownWaystones(final KnownWaystonesEvent event) {
        knownWaystones = event.getWaystones();
    }

    private static void updateClosestWaystone(final TickEvent.PlayerTickEvent event) {
        waystoneUpdateTimer++;

        if (waystoneUpdateTimer % 10 == 0) {
            String playerDimension = event.player.world.getDimensionKey().getLocation().toString();
            BlockPos playerPos = event.player.getPosition();
            double minDist = Double.MAX_VALUE;

            // Iterate waystones, finding closest one
            for (IWaystone waystone : knownWaystones) {
                String waystoneDimension = waystone.getDimension().getLocation().toString();

                // Only consider waystones with names
                if (!waystone.hasName()) continue;

                // Calculate distance for waystones in same dimension as player.
                if (playerDimension.equals(waystoneDimension)) {
                    double distance = waystone.getPos().distanceSq(playerPos);
                    if (distance < minDist) {
                        minDist = distance;
                        closestWaystone = waystone;
                    }
                }
            }

            // Only save closest waystone if it is within range
            int range = TTConfig.waystones.range.get();
            if (minDist > range * range) {
                closestWaystone = null;
            }
        }
    }

    /**
     * Initializes rendering the nearest waystone title if conditions are met.
     */
    public static boolean updateWaystoneTitle(PlayerEntity player) {
        // Invalid or missing closest waystone
        if (closestWaystone == null || !closestWaystone.hasName()) return waystoneTitleRenderer.titleTimer > 0;

        if (
            waystoneTitleRenderer.enabled &&
            waystoneTitleRenderer.cooldownTimer <= 0 &&
            !waystoneTitleRenderer.containsEntry(w -> w.getName().equals(closestWaystone.getName()))
        ) {
            if (
                waystoneTitleRenderer.displayedTitle == null ||
                !closestWaystone.getName().equals(waystoneTitleRenderer.displayedTitle.getString())
            ) {
                // We only need to update if title has changed
                waystoneTitleRenderer.setColor(waystoneTitleRenderer.titleDefaultTextColor);
                waystoneTitleRenderer.displayTitle(new StringTextComponent(closestWaystone.getName()), null);
                waystoneTitleRenderer.cooldownTimer = TTConfig.waystones.textCooldownTime.get();
                waystoneTitleRenderer.addRecentEntry(closestWaystone);

                // Play biome entry sound
                player.playSound(TTModSound.WAYSTONE, TTConfig.sound.waystoneVolume.get().floatValue(), TTConfig.sound.waystonePitch.get().floatValue());
            }
        }
        return waystoneTitleRenderer.titleTimer > 0;
    }

    public static void clientTick() {
        waystoneTitleRenderer.tick();
    }

    public static void renderText(float partialTicks, MatrixStack matrixStack) {
        waystoneTitleRenderer.renderText(partialTicks, matrixStack);
    }

    public static void reset() {
        waystoneTitleRenderer.clearTimer();
        waystoneTitleRenderer.recentEntries.clear();
    }

    public static void updateRendererFromConfig(ConfigWaystones config) {
        waystoneTitleRenderer.maxRecentListSize = config.recentWaystoneCacheSize.get();
        waystoneTitleRenderer.enabled = config.enabled.get();
        waystoneTitleRenderer.titleFadeInTicks = config.textFadeInTime.get();
        waystoneTitleRenderer.titleDisplayTime = config.textDisplayTime.get();
        waystoneTitleRenderer.titleFadeOutTicks = config.textFadeOutTime.get();
        waystoneTitleRenderer.titleDefaultTextColor = config.textColor.get();
        waystoneTitleRenderer.showTextShadow = config.renderShadow.get();
        waystoneTitleRenderer.titleTextSize = config.textSize.get().floatValue();
        waystoneTitleRenderer.titleXOffset = config.textXOffset.get().floatValue();
        waystoneTitleRenderer.titleYOffset = config.textYOffset.get().floatValue();
        waystoneTitleRenderer.isTextCentered = config.centerText.get();
    }
}
