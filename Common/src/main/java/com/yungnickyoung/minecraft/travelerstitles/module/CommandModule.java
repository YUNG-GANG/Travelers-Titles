package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCommand;

@AutoRegister(TravelersTitlesCommon.MOD_ID)
public class CommandModule {
    @AutoRegister("biome_title")
    public static AutoRegisterCommand BIOME_TITLE_COMMAND = AutoRegisterCommand.of(BiomeTitleCommand::register);

    @AutoRegister("dimension_title")
    public static AutoRegisterCommand DIMENSION_TITLE_COMMAND = AutoRegisterCommand.of(DimensionTitleCommand::register);

    @AutoRegister("reload")
    public static AutoRegisterCommand RELOAD_CONFIG_COMMAND = AutoRegisterCommand.of(ReloadConfigCommand::register);
}
