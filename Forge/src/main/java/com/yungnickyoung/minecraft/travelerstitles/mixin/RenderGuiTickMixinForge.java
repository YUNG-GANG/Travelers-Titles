package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class RenderGuiTickMixinForge {
    @Inject(method = "render", at = @At(value = "HEAD"))
    private void travelerstitles_onClientTick(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        TravelersTitlesCommon.titleManager.renderTitles(guiGraphics, partialTick);
    }
}
