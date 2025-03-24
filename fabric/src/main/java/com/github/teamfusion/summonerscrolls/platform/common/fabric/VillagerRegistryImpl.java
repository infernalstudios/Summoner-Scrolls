package com.github.teamfusion.summonerscrolls.platform.common.fabric;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.Collections;

public class VillagerRegistryImpl {
    public static void addTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        if (level < 1) throw new IllegalArgumentException("Profession Level must be positive");
        TradeOfferHelper.registerVillagerOffers(profession, level, defaultTrades -> Collections.addAll(defaultTrades, trades));
    }

    public static void addWandererTrade(boolean isRare, VillagerTrades.ItemListing... trades) {
        TradeOfferHelper.registerWanderingTraderOffers(isRare ? 2 : 1, defaultTrades -> Collections.addAll(defaultTrades, trades));
    }
}
