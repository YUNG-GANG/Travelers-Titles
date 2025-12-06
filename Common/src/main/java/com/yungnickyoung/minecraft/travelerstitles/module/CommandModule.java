package com.yungnickyoung.minecraft.travelerstitles.module;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandModule {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        BiomeTitleCommand.register(dispatcher, context, selection);
        DimensionTitleCommand.register(dispatcher, context, selection);
        ReloadConfigCommand.register(dispatcher, context, selection);
    }
}
