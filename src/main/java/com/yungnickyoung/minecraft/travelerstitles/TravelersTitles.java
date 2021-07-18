package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.init.TTModClient;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TravelersTitles.MOD_ID)
public class TravelersTitles {
    public static final String MOD_ID = "travelerstitles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public TravelersTitles() {
        init();
    }

    private void init() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModConfig::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModClient::init);
    }
}
