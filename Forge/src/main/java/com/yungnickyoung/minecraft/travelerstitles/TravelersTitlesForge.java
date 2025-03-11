package com.yungnickyoung.minecraft.travelerstitles;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(TravelersTitlesCommon.MOD_ID)
public class TravelersTitlesForge {
    public TravelersTitlesForge() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> TravelersTitlesCommon::init);
    }
}
