package com.yungnickyoung.minecraft.travelerstitles.command;//package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeTitleCommand {
    public static final DynamicCommandExceptionType BIOME_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
            (formatArgs) -> new TranslatableComponent("travelerstitles.commands.biometitle.notfound", formatArgs));

    public static final DynamicCommandExceptionType INVALID_BIOME_EXCEPTION = new DynamicCommandExceptionType(
            (formatArgs) -> new TranslatableComponent("travelerstitles.commands.biometitle.invalid", formatArgs));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("biometitle")
                .requires((source) -> source.hasPermission(2))
                .then(Commands.argument("biome", ResourceOrTagLocationArgument.resourceOrTag(Registry.BIOME_REGISTRY))
                .executes((context) -> displayTitle(context.getSource(), ResourceOrTagLocationArgument.getBiome(context, "biome")))));
    }

    public static int displayTitle(CommandSourceStack commandSource, ResourceOrTagLocationArgument.Result<Biome> biomeResult) throws CommandSyntaxException {
        ResourceLocation biomeBaseKey;
        try {
            biomeBaseKey = new ResourceLocation(biomeResult.asPrintable());
        } catch (ResourceLocationException e) {
            throw INVALID_BIOME_EXCEPTION.create(biomeResult.asPrintable());
        }

        String overrideBiomeNameKey = Util.makeDescriptionId(TravelersTitlesCommon.MOD_ID + ".biome", biomeBaseKey);
        String normalBiomeNameKey = Util.makeDescriptionId("biome", biomeBaseKey);

        if (TravelersTitlesCommon.CONFIG.biomes.biomeBlacklist.contains(biomeBaseKey.toString())) {
            commandSource.sendSuccess(new TextComponent("That biome is blacklisted, so its title won't normally show!"), false);
        }

        Component biomeTitle;

        // We will only display name if entry for biome found
        if (Language.getInstance().has(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
            biomeTitle = new TranslatableComponent(overrideBiomeNameKey);
        } else if (Language.getInstance().has(normalBiomeNameKey)) { // Next, check for normal biome lang entry
            biomeTitle = new TranslatableComponent(normalBiomeNameKey);
        } else {
            throw BIOME_NOT_FOUND_EXCEPTION.create(biomeResult.asPrintable());
        }

        // Get color of text for biome, if entry exists. Otherwise default to normal color
        String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
        String normalBiomeColorKey = normalBiomeNameKey + ".color";
        String biomeColorStr;
        if (Language.getInstance().has(overrideBiomeColorKey)) {
            biomeColorStr = Language.getInstance().getOrDefault(overrideBiomeColorKey);
        } else if (Language.getInstance().has(normalBiomeColorKey)) {
            biomeColorStr = Language.getInstance().getOrDefault(normalBiomeColorKey);
        } else {
            biomeColorStr = TravelersTitlesCommon.titleManager.biomeTitleRenderer.titleDefaultTextColor;
        }

        // Set display
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.setColor(biomeColorStr);
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.displayTitle(biomeTitle, null);
        TravelersTitlesCommon.titleManager.biomeTitleRenderer.cooldownTimer = TravelersTitlesCommon.CONFIG.biomes.textCooldownTime;

        return 1;

    }
}
