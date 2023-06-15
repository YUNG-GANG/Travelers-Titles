package com.yungnickyoung.minecraft.travelerstitles.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public interface IWaystonesCompatHelper {
    void init();
    boolean updateWaystoneTitle(Player player);
    void clientTick();
    void renderText(float partialTicks, GuiGraphics guiGraphics);
    void reset();
    boolean isRendering();
    void updateRendererFromConfig(ConfigModule.Waystones config);
}
