package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class TTModClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(TTModClient::playerTick);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::playerChangedimension);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::clientTick);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::renderTitles);
        MinecraftForge.EVENT_BUS.addListener(TTModClient::registerCommands);
    }

    public static void playerTick(final TickEvent.PlayerTickEvent event) {
        TravelersTitles.titleManager.playerTick(event);
    }

    public static void playerChangedimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        TravelersTitles.titleManager.playerChangedDimension();
    }

    public static void clientTick(TickEvent.ClientTickEvent event) {
        TravelersTitles.titleManager.clientTick(event);
    }

    public static void renderTitles(RenderGameOverlayEvent.Pre event) {
        TravelersTitles.titleManager.renderTitles(event);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        BiomeTitleCommand.register(event.getDispatcher());
        DimensionTitleCommand.register(event.getDispatcher());
        ReloadConfigCommand.register(event.getDispatcher());
    }
}
