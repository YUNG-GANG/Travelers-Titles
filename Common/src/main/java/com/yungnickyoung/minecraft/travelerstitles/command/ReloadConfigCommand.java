package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands
            .literal("tt_reload")
            .requires((source) -> source.hasPermission(2))
            .executes(context -> reloadConfig(context.getSource())));
    }

    public static int reloadConfig(CommandSourceStack commandSource) {
        Services.CONFIG_RELOADER.reloadConfig();
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.clearTimer();
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.recentEntries.clear();
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.clearTimer();
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.recentEntries.clear();
        if (CompatModule.isWaystonesLoaded) {
            Services.WAYSTONES.reset();
        }
        commandSource.sendSuccess(new TextComponent("Loading changes from Traveler's Titles config..."), false);
        return 1;
    }
}