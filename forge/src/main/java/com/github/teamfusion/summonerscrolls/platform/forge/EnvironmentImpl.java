package com.github.teamfusion.summonerscrolls.platform.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;

import java.nio.file.Path;
import java.util.function.Supplier;

@SuppressWarnings("NullableProblems")
public class EnvironmentImpl {

    public static boolean isClientSide() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    public static Path getConfigDir() {
        return FMLLoader.getGamePath().resolve("config/");
    }
}
