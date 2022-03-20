package com.yungnickyoung.minecraft.travelerstitles;

import net.fabricmc.api.ClientModInitializer;

public class TravelersTitlesFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TravelersTitlesCommon.init();
    }
}
