package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class RenderGuiNeoForge {
    public static void init(IEventBus eventBus) {
        eventBus.addListener(RenderGuiNeoForge::onClientTick);
    }

    @SubscribeEvent
    public static void onClientTick(RegisterGuiLayersEvent event) {
        event.registerAboveAll(TravelersTitlesCommon.id("overlay"), RenderGuiNeoForge::render);
    }

    private static void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        TravelersTitlesCommon.titleManager.renderTitles(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false));
    }
}
