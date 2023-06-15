package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class RenderGuiTickMixinFabric {
    @Inject(method = "render", at = @At(value = "HEAD"))
    private void travelerstitles_onClientTick(GuiGraphics guiGraphics, float partialTicks, CallbackInfo ci) {
        TravelersTitlesCommon.titleManager.renderTitles(guiGraphics, partialTicks);
    }
}
