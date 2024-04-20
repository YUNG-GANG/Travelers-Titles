package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;

@Mod.EventBusSubscriber(modid = TravelersTitlesCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderGuiNeoForge {
    @SubscribeEvent
    public static void onClientTick(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(new ResourceLocation(TravelersTitlesCommon.MOD_ID, "title"), RenderGuiNeoForge::render);
    }

    private static void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        TravelersTitlesCommon.titleManager.renderTitles(guiGraphics, partialTick);
    }
}
