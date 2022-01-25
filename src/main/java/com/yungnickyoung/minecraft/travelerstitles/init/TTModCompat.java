package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import net.minecraftforge.fml.ModList;

public class TTModCompat {
    public static boolean isWaystonesLoaded = false;

    public static void init() {
        if (ModList.get().isLoaded("waystones")) {
            WaystonesCompat.init();
            isWaystonesLoaded = true;
        }
    }
}
