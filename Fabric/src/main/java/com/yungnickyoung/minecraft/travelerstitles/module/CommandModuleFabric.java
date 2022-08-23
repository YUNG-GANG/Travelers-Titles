package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandModuleFabric {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> BiomeTitleCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> DimensionTitleCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ReloadConfigCommand.register(dispatcher));
    }
}
