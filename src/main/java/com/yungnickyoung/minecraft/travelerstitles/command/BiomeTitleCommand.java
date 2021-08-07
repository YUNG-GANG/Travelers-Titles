package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;

public class BiomeTitleCommand {
    public static final DynamicCommandExceptionType BIOME_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
        (formatArgs) -> new TranslationTextComponent("commands.locatebiome.invalid", formatArgs));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands
            .literal("biometitle").requires((source) -> source.hasPermissionLevel(2))
            .then(Commands.argument("biome", ResourceLocationArgument.resourceLocation())
                .suggests(SuggestionProviders.field_239574_d_)
                .executes((context) -> displayTitle(context.getSource(), context.getArgument("biome", ResourceLocation.class)))));
    }

    public static int displayTitle(CommandSource commandSource, ResourceLocation biomeId) throws CommandSyntaxException {
        Biome biome = commandSource.getServer().func_244267_aX().getRegistry(Registry.BIOME_KEY)
            .getOptional(biomeId)
            .orElseThrow(() -> BIOME_NOT_FOUND_EXCEPTION.create(biomeId));

        ResourceLocation biomeBaseKey = commandSource.getServer().func_244267_aX().getRegistry(Registry.BIOME_KEY).getKey(biome);
        String overrideBiomeNameKey = Util.makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
        String normalBiomeNameKey = Util.makeTranslationKey("biome", biomeBaseKey);

        if (TravelersTitles.titleManager.blacklistedBiomes.contains(biomeBaseKey.toString())) {
            commandSource.sendFeedback(new StringTextComponent("That biome is blacklisted, so its title won't normally show!"), false);
        }

        if (biomeBaseKey != null) {
            ITextComponent biomeTitle;

            // We will only display name if entry for biome found
            if (LanguageMap.getInstance().func_230506_b_(overrideBiomeNameKey)) { // First, check for a special user-provided override intended for TT use
                biomeTitle = new TranslationTextComponent(overrideBiomeNameKey);
            } else if (LanguageMap.getInstance().func_230506_b_(normalBiomeNameKey)) { // Next, check for normal biome lang entry
                biomeTitle = new TranslationTextComponent(normalBiomeNameKey);
            } else {
                return 0; // No entry found
            }

            // Get color of text for biome, if entry exists. Otherwise default to normal color
            String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
            String normalBiomeColorKey = normalBiomeNameKey + ".color";
            String biomeColorStr;
            if (LanguageMap.getInstance().func_230506_b_(overrideBiomeColorKey)) {
                biomeColorStr = LanguageMap.getInstance().func_230503_a_(overrideBiomeColorKey);
            } else if (LanguageMap.getInstance().func_230506_b_(normalBiomeColorKey)) {
                biomeColorStr = LanguageMap.getInstance().func_230503_a_(normalBiomeColorKey);
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
