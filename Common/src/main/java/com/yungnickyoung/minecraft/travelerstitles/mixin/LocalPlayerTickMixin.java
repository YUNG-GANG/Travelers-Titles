package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerTickMixin extends Player {
    public LocalPlayerTickMixin(Level $$0, BlockPos $$1, float $$2, GameProfile $$3) {
        super($$0, $$1, $$2, $$3);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void onPostPlayerTick(CallbackInfo ci) {
        TravelersTitlesCommon.titleManager.playerTick(this);
    }
}
