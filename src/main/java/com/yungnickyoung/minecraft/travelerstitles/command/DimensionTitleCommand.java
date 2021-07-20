package com.yungnickyoung.minecraft.travelerstitles.command;


import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModClient;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class DimensionTitleCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands
            .literal("dimensiontitle").requires((source) -> source.hasPermissionLevel(2))
            .then(Commands.argument("dimension", DimensionArgument.getDimension())
                .executes((context) -> displayTitle(context.getSource(), DimensionArgument.getDimensionArgument(context, "dimension")))));
    }

    public static int displayTitle(CommandSource commandSource, ServerWorld world) {
        // Get dimension key
        ResourceLocation dimensionBaseKey = world.getDimensionKey().getLocation();
        String dimensionNameKey = Util.makeTranslationKey(TravelersTitles.MOD_ID, dimensionBaseKey);

        if (TTModClient.blacklistedDimensions.contains(dimensionBaseKey.toString())) {
            commandSource.sendFeedback(new StringTextComponent("That dimension is blacklisted, so its title won't normally show!"), false);
        }

        ITextComponent dimensionTitle;
        dimensionTitle = LanguageMap.getInstance().func_230506_b_(dimensionNameKey)
            ? new TranslationTextComponent(dimensionNameKey)
            : new StringTextComponent("???"); // Display ??? for unknown dimensions;

        // Get color of text for dimension, if entry exists. Otherwise default to normal color
        String dimensionColorKey = dimensionNameKey + ".color";
        String dimensionColorStr = LanguageMap.getInstance().func_230506_b_(dimensionColorKey)
            ? LanguageMap.getInstance().func_230503_a_(dimensionColorKey)
            : TTModClient.dimensionTitleRenderer.titleDefaultTextColor;

        // Set display
        TTModClient.dimensionTitleRenderer.setColor(dimensionColorStr);
        TTModClient.dimensionTitleRenderer.displayTitle(dimensionTitle, null);

        return 1;
    }
}
