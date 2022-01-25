package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.mojang.authlib.GameProfile;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
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
    public LocalPlayerTickMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
        super(p_36114_, p_36115_, p_36116_, p_36117_);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void onPostPlayerTick(CallbackInfo ci) {
        TravelersTitles.titleManager.playerTick(this);
    }
}
