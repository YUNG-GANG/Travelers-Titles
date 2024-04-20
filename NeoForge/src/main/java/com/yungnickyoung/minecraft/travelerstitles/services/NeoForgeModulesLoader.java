package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleNeoForge;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules(); // Load common modules
        if (FMLLoader.getDist().isClient()) {
            ConfigModuleNeoForge.init();
        }
    }
}
