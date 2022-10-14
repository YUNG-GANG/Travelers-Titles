package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterSoundEvent;

@AutoRegister(TravelersTitlesCommon.MOD_ID)
public class SoundModule {
    @AutoRegister("biome")
    public static AutoRegisterSoundEvent BIOME = AutoRegisterSoundEvent.create();

    @AutoRegister("dimension")
    public static AutoRegisterSoundEvent DIMENSION = AutoRegisterSoundEvent.create();

    @AutoRegister("waystone")
    public static AutoRegisterSoundEvent WAYSTONE = AutoRegisterSoundEvent.create();
}
