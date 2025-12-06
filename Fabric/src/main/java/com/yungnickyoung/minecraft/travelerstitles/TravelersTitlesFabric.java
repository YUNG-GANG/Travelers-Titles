package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.CommandModule;
import com.yungnickyoung.minecraft.travelerstitles.module.SoundModule;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class TravelersTitlesFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register sounds
        SoundModule.init();

        // Register commands
        CommandRegistrationCallback.EVENT.register(CommandModule::registerCommands);

        // Initialize common
        TravelersTitlesCommon.init();
    }
}
