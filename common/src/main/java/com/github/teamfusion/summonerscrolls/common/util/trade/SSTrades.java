package com.github.teamfusion.summonerscrolls.common.util.trade;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.platform.common.VillagerRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;
import java.util.function.Supplier;

public class SSTrades {
    public static void init() {
        List<Supplier<Item>> items = ImmutableList.of(SummonerItems.ZOMBIE_SCROLL, SummonerItems.SPIDER_SCROLL, SummonerItems.SKELETON_SCROLL, SummonerItems.BEE_SCROLL);

        // for some reason, forge doesn't want to accept EmeraldForItems, so we have to get the offer manually for some reason... may try implementing the listen() method in a future.
        items.forEach(item -> VillagerRegistry.addTrade(VillagerProfession.CLERIC, 1, (entity, random) -> new MerchantOffer(new ItemStack(item.get(), 1), new ItemStack(Items.EMERALD), 5, 2, 0.05F)));
    }
}