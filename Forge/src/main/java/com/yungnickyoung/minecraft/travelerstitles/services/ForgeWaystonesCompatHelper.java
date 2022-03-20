package com.yungnickyoung.minecraft.travelerstitles.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModule;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.api.KnownWaystonesEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class ForgeWaystonesCompatHelper implements IWaystonesCompatHelper {
    private List<IWaystone> knownWaystones = new ArrayList<>();
    private IWaystone closestWaystone;
    private int waystoneUpdateTimer = 0;
    private final TitleRenderer<IWaystone> waystoneTitleRenderer = new TitleRenderer<>(
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
        MinecraftForge.EVENT_BUS.addListener(this::updateKnownWaystones);
        MinecraftForge.EVENT_BUS.addListener(this::updateClosestWaystone);
    }

    /**
     * Updates the stored player's list of known waystones.
     */
    private void updateKnownWaystones(final KnownWaystonesEvent event) {
        knownWaystones = event.getWaystones();
    }

    private void updateClosestWaystone(final TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        waystoneUpdateTimer++;

        if (waystoneUpdateTimer % 10 == 0) {
            String playerDimension = player.level.dimension().location().toString();
            BlockPos playerPos = player.blockPosition();
            double minSqDist = Double.MAX_VALUE;

            // Iterate waystones, finding closest one
            for (IWaystone waystone : knownWaystones) {
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
        IWaystone _closestWaystone = closestWaystone;

        // Invalid or missing closest waystone
        if (_closestWaystone == null || !_closestWaystone.hasName()) return waystoneTitleRenderer.titleTimer > 0;

        if (
                waystoneTitleRenderer.enabled &&
                        waystoneTitleRenderer.cooldownTimer <= 0 &&
                        !waystoneTitleRenderer.matchesAnyRecentEntry(w -> w.getName().equals(_closestWaystone.getName()))
        ) {
            if (
                    waystoneTitleRenderer.displayedTitle == null ||
                            !_closestWaystone.getName().equals(waystoneTitleRenderer.displayedTitle.getString())
            ) { // We only need to update if title has changed
                waystoneTitleRenderer.setColor(waystoneTitleRenderer.titleDefaultTextColor);
                waystoneTitleRenderer.displayTitle(new TextComponent(_closestWaystone.getName()), null);
                waystoneTitleRenderer.cooldownTimer = TravelersTitlesCommon.CONFIG.waystones.textCooldownTime;
                waystoneTitleRenderer.addRecentEntry(_closestWaystone);

                // Play waystone entry sound if we haven't just changed dimensions.
                // This ensures the waystone sound won't overlap with the dimension sound.
                if (TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleTimer <= 0) {
                    player.playSound(SoundModule.WAYSTONE, (float) TravelersTitlesCommon.CONFIG.sound.waystoneVolume, (float) TravelersTitlesCommon.CONFIG.sound.waystonePitch);
                }
            }
        }
        return waystoneTitleRenderer.titleTimer > 0;
    }

    @Override
    public void clientTick() {
        waystoneTitleRenderer.tick();
    }

    @Override
    public void renderText(float partialTicks, PoseStack matrixStack) {
        waystoneTitleRenderer.renderText(partialTicks, matrixStack);
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
        waystoneTitleRenderer.titleXOffset = (float) config.textXOffset;
        waystoneTitleRenderer.titleYOffset = (float) config.textYOffset;
        waystoneTitleRenderer.isTextCentered = config.centerText;
    }
}
