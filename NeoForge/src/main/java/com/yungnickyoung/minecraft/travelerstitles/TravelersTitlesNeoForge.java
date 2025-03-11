package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleNeoForge;
import com.yungnickyoung.minecraft.travelerstitles.module.RenderGuiNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(value = TravelersTitlesCommon.MOD_ID, dist = Dist.CLIENT)
public class TravelersTitlesNeoForge {
    public static IEventBus loadingContextEventBus;

    public TravelersTitlesNeoForge(IEventBus eventBus, ModContainer container) {
        TravelersTitlesNeoForge.loadingContextEventBus = eventBus;

        TravelersTitlesCommon.init();
        ConfigModuleNeoForge.init(container);
        RenderGuiNeoForge.init(eventBus);
    }
}
