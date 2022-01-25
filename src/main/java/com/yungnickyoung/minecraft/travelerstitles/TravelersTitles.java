package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModCommands;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TravelersTitles implements ClientModInitializer {
    public static final String MOD_ID = "travelerstitles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /** Traveler's Titles config. Uses AutoConfig. **/
    public static TTConfig CONFIG;

    public static TitleRenderManager titleManager = new TitleRenderManager();

    @Override
    public void onInitializeClient() {
        TTModConfig.init();
        TTModCommands.init();
        TTModSound.init();
    }
}
