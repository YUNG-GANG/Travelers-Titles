package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.init.TTModCommands;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModCompat;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TravelersTitles.MOD_ID)
public class TravelersTitles {
    public static final String MOD_ID = "travelerstitles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static TitleRenderManager titleManager = new TitleRenderManager();

    public TravelersTitles() {
        init();
    }

    private void init() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModConfig::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModCompat::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModCommands::init);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TTModSound::init);
    }
}
