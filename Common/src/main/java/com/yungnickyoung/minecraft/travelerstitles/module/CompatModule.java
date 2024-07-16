package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.services.Services;

public class CompatModule {
    public static boolean isWaystonesLoaded = false;

    public static void init() {
        // We only support the Forge and NeoForge version of Waystones currently
        String platformName = Services.PLATFORM.getPlatformName();
        if (Services.PLATFORM.isModLoaded("waystones")
                && (platformName.equals("Forge") || platformName.equals("NeoForge"))) {
            Services.WAYSTONES.init();
            isWaystonesLoaded = true;
        }
    }
}
