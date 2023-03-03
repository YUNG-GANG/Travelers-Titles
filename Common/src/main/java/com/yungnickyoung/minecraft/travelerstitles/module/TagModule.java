package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TagModule {
    public static final TagKey<Biome> IS_UNDERGROUND = TagKey.create(Registries.BIOME,
            new ResourceLocation(TravelersTitlesCommon.MOD_ID, "is_underground"));
}
