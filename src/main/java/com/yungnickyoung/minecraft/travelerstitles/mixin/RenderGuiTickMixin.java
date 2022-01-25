package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class RenderGuiTickMixin {
    @Inject(method = "render", at = @At(value = "HEAD"))
    private void onClientTick(PoseStack poseStack, float partialTicks, CallbackInfo ci) {
        TravelersTitles.titleManager.renderTitles(poseStack, partialTicks);
    }
}
