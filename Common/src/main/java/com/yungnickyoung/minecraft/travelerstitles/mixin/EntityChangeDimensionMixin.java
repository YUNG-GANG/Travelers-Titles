package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityChangeDimensionMixin {
    @Inject(method = "changeDimension", at = @At(value = "TAIL"))
    private void onPlayerChangeDimension(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
        TravelersTitlesCommon.titleManager.playerChangedDimension(this);
    }
}
