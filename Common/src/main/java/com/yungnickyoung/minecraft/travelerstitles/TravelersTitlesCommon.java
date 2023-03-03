package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.module.ConfigModule;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;
import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TravelersTitlesCommon {
    public static final String MOD_ID = "travelerstitles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final ConfigModule CONFIG = new ConfigModule();

    public static TitleRenderManager titleManager = new TitleRenderManager();

    public static void init() {
        YungAutoRegister.scanPackageForAnnotations("com.yungnickyoung.minecraft.travelerstitles.module");
        Services.MODULES.loadModules();
    }
}
