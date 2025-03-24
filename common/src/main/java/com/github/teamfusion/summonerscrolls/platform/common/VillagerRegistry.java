package com.github.teamfusion.summonerscrolls.platform.common;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class VillagerRegistry {
    @ExpectPlatform
    public static void addTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addWandererTrade(boolean isRare, VillagerTrades.ItemListing... trades) {
        throw new AssertionError();
    }
}