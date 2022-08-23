package com.yungnickyoung.minecraft.travelerstitles.module;

import net.minecraft.core.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.RegisterEvent;

public class SoundModuleForge {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(SoundModuleForge::registerSoundEvents);
    }

    private static void registerSoundEvents(final RegisterEvent event) {
        event.register(Registry.SOUND_EVENT_REGISTRY, helper -> {
            helper.register(SoundModule.BIOME.getLocation(), SoundModule.BIOME);
            helper.register(SoundModule.DIMENSION.getLocation(), SoundModule.DIMENSION);
            helper.register(SoundModule.WAYSTONE.getLocation(), SoundModule.WAYSTONE);
        });
    }
}
