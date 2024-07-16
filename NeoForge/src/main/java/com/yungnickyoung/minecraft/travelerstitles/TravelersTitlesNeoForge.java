package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleNeoForge;
import com.yungnickyoung.minecraft.travelerstitles.module.RenderGuiNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(TravelersTitlesCommon.MOD_ID)
public class TravelersTitlesNeoForge {
    public static IEventBus loadingContextEventBus;

    public TravelersTitlesNeoForge(IEventBus eventBus, ModContainer container) {
        TravelersTitlesNeoForge.loadingContextEventBus = eventBus;

        TravelersTitlesCommon.init();
        if (FMLLoader.getDist().isClient()) {
            ConfigModuleNeoForge.init(container);
            RenderGuiNeoForge.init(eventBus);
        }
    }
}
