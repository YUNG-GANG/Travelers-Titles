package com.yungnickyoung.minecraft.travelerstitles.command;


import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class DimensionTitleCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands
            .literal("dimensiontitle").requires((source) -> source.hasPermission(2))
            .then(Commands.argument("dimension", DimensionArgument.dimension())
                .executes((context) -> displayTitle(context.getSource(), DimensionArgument.getDimension(context, "dimension")))));
    }

    public static int displayTitle(CommandSourceStack commandSource, ServerLevel world) {
        // Get dimension key
        ResourceLocation dimensionBaseKey = world.dimension().location();
        String dimensionNameKey = Util.makeDescriptionId(TravelersTitles.MOD_ID, dimensionBaseKey);

        if (TravelersTitles.titleManager.blacklistedDimensions.contains(dimensionBaseKey.toString())) {
            commandSource.sendSuccess(new TextComponent("That dimension is blacklisted, so its title won't normally show!"), false);
        }

        Component dimensionTitle;
        dimensionTitle = Language.getInstance().has(dimensionNameKey)
            ? new TranslatableComponent(dimensionNameKey)
            : new TextComponent("???"); // Display ??? for unknown dimensions;

        // Get color of text for dimension, if entry exists. Otherwise default to normal color
        String dimensionColorKey = dimensionNameKey + ".color";
        String dimensionColorStr = Language.getInstance().has(dimensionColorKey)
            ? Language.getInstance().getOrDefault(dimensionColorKey)
            : TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor;

        // Set display
        TravelersTitles.titleManager.dimensionTitleRenderer.setColor(dimensionColorStr);
        TravelersTitles.titleManager.dimensionTitleRenderer.displayTitle(dimensionTitle, null);

        return 1;
    }
}
