package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleForge;

public class ForgeConfigReloader implements IConfigReloader {
    @Override
    public void reloadConfig() {
        ConfigModuleForge.bakeConfig();
        ConfigModule.updateRenderersFromConfig();
    }
}
