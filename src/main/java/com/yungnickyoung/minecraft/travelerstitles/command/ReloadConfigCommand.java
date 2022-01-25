package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
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
        TravelersTitles.CONFIG.validatePostLoad();
        TravelersTitles.titleManager.biomeTitleRenderer.clearTimer();
        TravelersTitles.titleManager.biomeTitleRenderer.recentEntries.clear();
        TravelersTitles.titleManager.dimensionTitleRenderer.clearTimer();
        TravelersTitles.titleManager.dimensionTitleRenderer.recentEntries.clear();
        commandSource.sendSuccess(new TextComponent("Loading changes from Traveler's Titles config..."), false);
        return 1;
    }
}