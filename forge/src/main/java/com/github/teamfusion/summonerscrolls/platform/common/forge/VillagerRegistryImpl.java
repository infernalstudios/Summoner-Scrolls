package com.github.teamfusion.summonerscrolls.platform.common.forge;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = SummonerScrolls.MOD_ID)
public class VillagerRegistryImpl {
    private static final Map<VillagerProfession, Int2ObjectMap<List<VillagerTrades.ItemListing>>> TRADES_TO_ADD = new HashMap<>();
    private static final List<VillagerTrades.ItemListing> WANDERER_TRADER_TRADES_GENERIC = new ArrayList<>();
    private static final List<VillagerTrades.ItemListing> WANDERER_TRADER_TRADES_RARE = new ArrayList<>();

    public static void addTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        if (level < 1) throw new IllegalArgumentException("Profession Level must be positive");
        Int2ObjectMap<List<VillagerTrades.ItemListing>> tradesForProfession = TRADES_TO_ADD.computeIfAbsent(profession, $ -> new Int2ObjectOpenHashMap<>());
        List<VillagerTrades.ItemListing> tradesForLevel = tradesForProfession.computeIfAbsent(level, $ -> new ArrayList<>());
        Collections.addAll(tradesForLevel, trades);
    }

    public static void addWandererTrade(boolean isRare, VillagerTrades.ItemListing... trades) {
        if (isRare) {
            Collections.addAll(WANDERER_TRADER_TRADES_RARE, trades);
        } else {
            Collections.addAll(WANDERER_TRADER_TRADES_GENERIC, trades);
        }
    }

    @SubscribeEvent
    public static void onTrading(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = TRADES_TO_ADD.get(event.getType());

        if (trades != null) {
            for (Int2ObjectMap.Entry<List<VillagerTrades.ItemListing>> entry : trades.int2ObjectEntrySet()) {
                event.getTrades().computeIfAbsent(entry.getIntKey(), $ -> NonNullList.create()).addAll(entry.getValue());
            }
        }
    }

    @SubscribeEvent
    public static void onTrading(WandererTradesEvent event) {
        if (!WANDERER_TRADER_TRADES_GENERIC.isEmpty()) {
            event.getGenericTrades().addAll(WANDERER_TRADER_TRADES_GENERIC);
        }
        if (!WANDERER_TRADER_TRADES_RARE.isEmpty()) {
            event.getRareTrades().addAll(WANDERER_TRADER_TRADES_RARE);
        }
    }
}
