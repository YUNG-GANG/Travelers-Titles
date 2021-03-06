package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CommandModuleForge {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(CommandModuleForge::registerCommands);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        BiomeTitleCommand.register(event.getDispatcher());
        DimensionTitleCommand.register(event.getDispatcher());
        ReloadConfigCommand.register(event.getDispatcher());
    }
}
