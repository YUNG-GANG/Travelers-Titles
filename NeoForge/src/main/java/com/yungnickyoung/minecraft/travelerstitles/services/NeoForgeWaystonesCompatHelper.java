package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModule;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.event.WaystonesListReceivedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NeoForgeWaystonesCompatHelper implements IWaystonesCompatHelper {
    private List<Waystone> knownWaystones = new ArrayList<>();
    private Set<Waystone> sharestones = new HashSet<>();
    private Waystone closestWaystone;
    private int waystoneUpdateTimer = 0;
    private final TitleRenderer<Waystone> waystoneTitleRenderer = new TitleRenderer<>(
            TravelersTitlesCommon.CONFIG.waystones.recentWaystoneCacheSize,
            TravelersTitlesCommon.CONFIG.waystones.enabled,
            TravelersTitlesCommon.CONFIG.waystones.textFadeInTime,
            TravelersTitlesCommon.CONFIG.waystones.textDisplayTime,
            TravelersTitlesCommon.CONFIG.waystones.textFadeOutTime,
            TravelersTitlesCommon.CONFIG.waystones.textColor,
            TravelersTitlesCommon.CONFIG.waystones.renderShadow,
            TravelersTitlesCommon.CONFIG.waystones.textSize,
            TravelersTitlesCommon.CONFIG.waystones.textXOffset,
            TravelersTitlesCommon.CONFIG.waystones.textYOffset,
            TravelersTitlesCommon.CONFIG.waystones.centerText
    );

    @Override
    public void init() {
        NeoForge.EVENT_BUS.addListener(this::onWaystoneListReceived);
        NeoForge.EVENT_BUS.addListener(this::updateClosestWaystone);
    }

    /**
     * Updates the stored player's list of known waystones.
     */
    private void onWaystoneListReceived(final WaystonesListReceivedEvent event) {
        if (event.getWaystoneType().equals(WaystoneTypes.WAYSTONE)) {
            knownWaystones = event.getWaystones();
        } else if (WaystoneTypes.isSharestone(event.getWaystoneType())) {
            sharestones.addAll(event.getWaystones());
        }
    }

    private void updateClosestWaystone(final TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        waystoneUpdateTimer++;

        if (waystoneUpdateTimer % 10 == 0) {
            String playerDimension = player.level().dimension().location().toString();
            BlockPos playerPos = player.blockPosition();
            double minSqDist = Double.MAX_VALUE;

            // Iterate waystones, finding closest one
            for (Waystone waystone : knownWaystones) {
                String waystoneDimension = waystone.getDimension().location().toString();

                // Only consider waystones with names
                if (!waystone.hasName()) continue;

                // Calculate distance for waystones in same dimension as player.
                if (playerDimension.equals(waystoneDimension)) {
                    double sqDistance = waystone.getPos().distSqr(playerPos);
                    if (sqDistance < minSqDist) {
                        minSqDist = sqDistance;
                        closestWaystone = waystone;
                    }
                }
            }

            // Iterate sharestones, finding closest one
            for (Waystone sharestone : sharestones) {
                String sharestoneDimension = sharestone.getDimension().location().toString();

                // Only consider sharestones with names
                if (!sharestone.hasName()) continue;

                // Calculate distance for sharestones in same dimension as player.
                if (playerDimension.equals(sharestoneDimension)) {
                    double sqDistance = sharestone.getPos().distSqr(playerPos);
                    if (sqDistance < minSqDist) {
                        minSqDist = sqDistance;
                        closestWaystone = sharestone;
                    }
                }
            }

            // Only save closest waystone if it is within range
            int range = TravelersTitlesCommon.CONFIG.waystones.range;
            if (minSqDist > range * range) {
                closestWaystone = null;
            }
        }
    }

    @Override
    public boolean updateWaystoneTitle(Player player) {
        // Store copy of closest waystone since it could become null at any time (from updateClosestWaystone event)
        Waystone _closestWaystone = closestWaystone;

        // Invalid or missing closest waystone
        if (_closestWaystone == null || !_closestWaystone.hasName()) {
            return waystoneTitleRenderer.titleTimer > 0;
        }

        boolean shouldUpdateTitle = waystoneTitleRenderer.enabled
                && waystoneTitleRenderer.cooldownTimer <= 0
                && !waystoneTitleRenderer.matchesAnyRecentEntry(w -> w.getName().equals(_closestWaystone.getName()));

        boolean hasTitleChanged = waystoneTitleRenderer.displayedTitle == null
                || !_closestWaystone.getName().equals(waystoneTitleRenderer.displayedTitle.getString());

        // We only need to update if title has changed
        if (shouldUpdateTitle && hasTitleChanged) {
            waystoneTitleRenderer.setColor(waystoneTitleRenderer.titleDefaultTextColor);
            waystoneTitleRenderer.displayTitle(_closestWaystone.getName(), null);
            waystoneTitleRenderer.cooldownTimer = TravelersTitlesCommon.CONFIG.waystones.textCooldownTime;
            waystoneTitleRenderer.addRecentEntry(_closestWaystone);

            // Play waystone entry sound if we haven't just changed dimensions.
            // This ensures the waystone sound won't overlap with the dimension sound.
            if (TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleTimer <= 0) {
                player.playSound(SoundModule.WAYSTONE.get(), (float) TravelersTitlesCommon.CONFIG.sound.waystoneVolume, (float) TravelersTitlesCommon.CONFIG.sound.waystonePitch);
            }
        }
        return waystoneTitleRenderer.titleTimer > 0;
    }

    @Override
    public void clientTick() {
        waystoneTitleRenderer.tick();
    }

    @Override
    public void renderText(float partialTicks, GuiGraphics guiGraphics) {
        waystoneTitleRenderer.renderText(partialTicks, guiGraphics);
    }

    @Override
    public void reset() {
        waystoneTitleRenderer.clearTimer();
        waystoneTitleRenderer.recentEntries.clear();
        waystoneTitleRenderer.displayedTitle = null;
    }

    @Override
    public boolean isRendering() {
        return waystoneTitleRenderer.titleTimer > 0;
    }

    @Override
    public void updateRendererFromConfig(ConfigModule.Waystones config) {
        waystoneTitleRenderer.maxRecentListSize = config.recentWaystoneCacheSize;
        waystoneTitleRenderer.enabled = config.enabled;
        waystoneTitleRenderer.titleFadeInTicks = config.textFadeInTime;
        waystoneTitleRenderer.titleDisplayTime = config.textDisplayTime;
        waystoneTitleRenderer.titleFadeOutTicks = config.textFadeOutTime;
        waystoneTitleRenderer.titleDefaultTextColor = config.textColor;
        waystoneTitleRenderer.showTextShadow = config.renderShadow;
        waystoneTitleRenderer.titleTextSize = (float) config.textSize;
        waystoneTitleRenderer.titleXOffset = config.textXOffset;
        waystoneTitleRenderer.titleYOffset = config.textYOffset;
        waystoneTitleRenderer.isTextCentered = config.centerText;
    }
}
