package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;

public interface IModulesLoader {
    default void loadModules() {
        CompatModule.init();
    }
}
