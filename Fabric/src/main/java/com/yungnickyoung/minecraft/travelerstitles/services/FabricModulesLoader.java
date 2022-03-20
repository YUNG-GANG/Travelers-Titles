package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.CommandModuleFabric;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleFabric;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        ConfigModuleFabric.init();
        CommandModuleFabric.init();
        SoundModuleFabric.init();
    }
}
