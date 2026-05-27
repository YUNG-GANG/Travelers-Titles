package com.yungnickyoung.minecraft.travelerstitles.command;


import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.Util;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;

public class DimensionTitleCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(Commands
            .literal("dimensiontitle")
            .requires((source) -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
            .then(Commands.argument("dimension", DimensionArgument.dimension())
                .executes((ctx) -> displayTitle(ctx.getSource(), DimensionArgument.getDimension(ctx, "dimension")))));
    }

    public static int displayTitle(CommandSourceStack commandSource, ServerLevel world) {
        // Get dimension key
        Identifier dimensionBaseKey = world.dimension().identifier();
        String dimensionNameKey = Util.makeDescriptionId(TravelersTitlesCommon.MOD_ID, dimensionBaseKey);

        if (TravelersTitlesCommon.CONFIG.dimensions.dimensionBlacklist.contains(dimensionBaseKey.toString())) {
            commandSource.sendSuccess(() -> Component.literal("That dimension is blacklisted, so its title won't normally show!"), false);
        }

        Component dimensionTitle;
        dimensionTitle = Language.getInstance().has(dimensionNameKey)
            ? Component.translatable(dimensionNameKey)
            : Component.literal("???"); // Display ??? for unknown dimensions;

        // Get color of text for dimension, if entry exists. Otherwise default to normal color
        String dimensionColorKey = dimensionNameKey + ".color";
        String dimensionColorStr = Language.getInstance().has(dimensionColorKey)
            ? Language.getInstance().getOrDefault(dimensionColorKey)
            : TravelersTitlesCommon.titleManager.dimensionTitleRenderer.titleDefaultTextColor;

        // Set display
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.setColor(dimensionColorStr);
        TravelersTitlesCommon.titleManager.dimensionTitleRenderer.displayTitle(dimensionTitle, null);

        return 1;
    }
}
