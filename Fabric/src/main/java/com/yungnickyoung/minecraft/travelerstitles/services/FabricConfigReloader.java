package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleFabric;

public class FabricConfigReloader implements IConfigReloader {

    @Override
    public void reloadConfig() {
        ConfigModuleFabric.updateConfig();
    }
}
