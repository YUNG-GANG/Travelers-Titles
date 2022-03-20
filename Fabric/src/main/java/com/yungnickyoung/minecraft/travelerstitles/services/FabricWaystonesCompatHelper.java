package com.yungnickyoung.minecraft.travelerstitles.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import net.minecraft.world.entity.player.Player;

public class FabricWaystonesCompatHelper implements IWaystonesCompatHelper {
    @Override
    public void init() {
    }

    @Override
    public boolean updateWaystoneTitle(Player player) {
        return false;
    }

    @Override
    public void clientTick() {
    }

    @Override
    public void renderText(float partialTicks, PoseStack matrixStack) {
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean isRendering() {
        return false;
    }

    @Override
    public void updateRendererFromConfig(ConfigModule.Waystones config) {
    }
}
