package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.CommandModuleForge;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleForge;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModuleForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ConfigModuleForge::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> CommandModuleForge::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SoundModuleForge::init);
    }
}
