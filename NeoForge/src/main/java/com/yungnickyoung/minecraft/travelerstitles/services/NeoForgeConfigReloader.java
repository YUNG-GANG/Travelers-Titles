package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleNeoForge;

public class NeoForgeConfigReloader implements IConfigReloader {
    @Override
    public void reloadConfig() {
        ConfigModuleNeoForge.bakeConfig();
        ConfigModule.updateRenderersFromConfig();
    }
}
