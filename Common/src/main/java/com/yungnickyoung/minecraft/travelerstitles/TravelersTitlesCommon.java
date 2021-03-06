package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;
import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TravelersTitlesCommon {
    public static final String MOD_ID = "travelerstitles";
    public static final String MOD_NAME = "Traveler's Titles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ConfigModule CONFIG = new ConfigModule();

    public static TitleRenderManager titleManager = new TitleRenderManager();

    public static void init() {
        CompatModule.init();
        Services.MODULES.loadModules();
    }
}
