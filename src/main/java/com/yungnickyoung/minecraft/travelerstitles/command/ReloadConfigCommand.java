package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.ModList;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands
            .literal("tt_reload")
            .requires((source) -> source.hasPermissionLevel(2))
            .executes(context -> {
                TTModConfig.reloadConfig();
                TravelersTitles.titleManager.biomeTitleRenderer.clearTimer();
                TravelersTitles.titleManager.biomeTitleRenderer.recentEntries.clear();
                TravelersTitles.titleManager.dimensionTitleRenderer.clearTimer();
                TravelersTitles.titleManager.dimensionTitleRenderer.recentEntries.clear();
                if (ModList.get().isLoaded("waystones")) {
                    WaystonesCompat.reset();
                }
                context.getSource().sendFeedback(new StringTextComponent("Loading changes from Traveler's Titles config..."), false);
                return 1;
            }));
    }
}