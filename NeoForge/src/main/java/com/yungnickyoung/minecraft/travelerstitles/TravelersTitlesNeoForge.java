package com.yungnickyoung.minecraft.travelerstitles;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TravelersTitlesCommon.MOD_ID)
public class TravelersTitlesNeoForge {
    public static IEventBus loadingContextEventBus;

    public TravelersTitlesNeoForge(IEventBus eventBus) {
        TravelersTitlesNeoForge.loadingContextEventBus = eventBus;

        TravelersTitlesCommon.init();
    }
}
