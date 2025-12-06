package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.CommandModule;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModuleNeoForge;
import com.yungnickyoung.minecraft.travelerstitles.module.RenderGuiNeoForge;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModule;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(value = TravelersTitlesCommon.MOD_ID, dist = Dist.CLIENT)
public class TravelersTitlesNeoForge {
    public static IEventBus loadingContextEventBus;

    public TravelersTitlesNeoForge(IEventBus eventBus, ModContainer container) {
        TravelersTitlesNeoForge.loadingContextEventBus = eventBus;

        // Register sounds
        SoundModule.init();

        // Register commands
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);

        TravelersTitlesCommon.init();
        ConfigModuleNeoForge.init(container);
        RenderGuiNeoForge.init(eventBus);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandModule.registerCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }
}
