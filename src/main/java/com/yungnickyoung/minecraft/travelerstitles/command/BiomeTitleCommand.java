package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BiomeTitleCommand {
    public static final DynamicCommandExceptionType BIOME_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
        (formatArgs) -> new TranslatableComponent("commands.locatebiome.invalid", formatArgs));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands
            .literal("biometitle").requires((source) -> source.hasPermission(2))
            .then(Commands.argument("biome", ResourceLocationArgument.id())
                .suggests(SuggestionProviders.AVAILABLE_BIOMES)
                .executes((context) -> displayTitle(context.getSource(), context.getArgument("biome", ResourceLocation.class)))));
    }

    public static int displayTitle(CommandSourceStack commandSource, ResourceLocation biomeId) throws CommandSyntaxException {
        Biome biome = commandSource.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY)
            .getOptional(biomeId)
            .orElseThrow(() -> BIOME_NOT_FOUND_EXCEPTION.create(biomeId));

        ResourceLocation biomeBaseKey = commandSource.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(biome);
        String overrideBiomeNameKey = Util.makeDescriptionId(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
        String normalBiomeNameKey = Util.makeDescriptionId("biome", biomeBaseKey);

        if (TravelersTitles.titleManager.blacklistedBiomes.contains(biomeBaseKey.toString())) {
            commandSource.sendSuccess(new TextComponent("That biome is blacklisted, so its title won't normally show!"), false);
        }

        if (biomeBaseKey != null) {
            Component biomeTitle;

            // We will only display name if entry for biome found
            if (Language.getInstance().has(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
                biomeTitle = new TranslatableComponent(overrideBiomeNameKey);
            } else if (Language.getInstance().has(normalBiomeNameKey)) { // Next, check for normal biome lang entry
                biomeTitle = new TranslatableComponent(normalBiomeNameKey);
            } else {
                return 0; // No entry found
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
                biomeColorStr = TravelersTitles.titleManager.biomeTitleRenderer.titleDefaultTextColor;
            }

            // Set display
            TravelersTitles.titleManager.biomeTitleRenderer.setColor(biomeColorStr);
            TravelersTitles.titleManager.biomeTitleRenderer.displayTitle(biomeTitle, null);
            TravelersTitles.titleManager.biomeTitleRenderer.cooldownTimer = TTConfig.biomes.textCooldownTime.get();

            return 1;
        }

        return 0;
    }
}
