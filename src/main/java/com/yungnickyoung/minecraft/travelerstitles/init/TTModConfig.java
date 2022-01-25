package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class TTModConfig {
    public static void init() {
        AutoConfig.register(TTConfig.class, Toml4jConfigSerializer::new);
        TravelersTitles.CONFIG = AutoConfig.getConfigHolder(TTConfig.class).getConfig();
    }
}
