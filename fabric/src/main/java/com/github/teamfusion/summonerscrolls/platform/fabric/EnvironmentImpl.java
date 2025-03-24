package com.github.teamfusion.summonerscrolls.platform.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class EnvironmentImpl {
    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}