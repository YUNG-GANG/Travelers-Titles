package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.services.Services;

public class CompatModule {
    public static boolean isWaystonesLoaded = false;

    public static void init() {
        // We only support the Forge version of Waystones currently
        if (Services.PLATFORM.isModLoaded("waystones") && Services.PLATFORM.getPlatformName().equals("Forge")) {
            Services.WAYSTONES.init();
            isWaystonesLoaded = true;
        }
    }
}
