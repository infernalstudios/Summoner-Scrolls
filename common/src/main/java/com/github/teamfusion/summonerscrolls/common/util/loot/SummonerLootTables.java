package com.github.teamfusion.summonerscrolls.common.util.loot;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.platform.common.LootRegistry;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SummonerLootTables {
    public static final Collection<ResourceLocation> TIER_ONE_SCROLL_TABLES = Set.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.WOODLAND_MANSION);
    public static final Collection<ResourceLocation> TIER_TWO_SCROLL_TABLES = Set.of(BuiltInLootTables.BASTION_TREASURE, BuiltInLootTables.BASTION_BRIDGE, BuiltInLootTables.BASTION_HOGLIN_STABLE, BuiltInLootTables.BASTION_OTHER, BuiltInLootTables.WOODLAND_MANSION);
    public static final Collection<ResourceLocation> TIER_THREE_SCROLL_TABLES = Set.of(BuiltInLootTables.END_CITY_TREASURE, BuiltInLootTables.WOODLAND_MANSION);

    private static final Map<Collection<ResourceLocation>, List<Supplier<Item>>> LOOT_PER_TIER = ImmutableMap.of(
            TIER_ONE_SCROLL_TABLES, List.of(SummonerItems.ZOMBIE_SCROLL, SummonerItems.SPIDER_SCROLL, SummonerItems.SPIDER_JOCKEY_SCROLL, SummonerItems.SKELETON_SCROLL, SummonerItems.BEE_SCROLL),
            TIER_TWO_SCROLL_TABLES, List.of(SummonerItems.HUSK_SCROLL, SummonerItems.STRAY_SCROLL, SummonerItems.CAVE_SPIDER_SCROLL, SummonerItems.ENDERMAN_SCROLL, SummonerItems.PIGLIN_SCROLL),
            TIER_THREE_SCROLL_TABLES, List.of(SummonerItems.CREEPER_SCROLL, SummonerItems.PIGLIN_BRUTE_SCROLL, SummonerItems.SHULKERMAN_SCROLL, SummonerItems.IRON_GOLEM_SCROLL, SummonerItems.CHARGED_CREEPER_SCROLL)
    );

    public static void init() {
        LootRegistry.modify((tables, location, context, builtin) -> {
            LOOT_PER_TIER.forEach((lootTable, items) -> {
                if (lootTable.contains(location)) {
                    items.forEach(item -> {
                        LootPool.Builder pool = LootPool.lootPool().add(LootItem.lootTableItem(item.get())).setRolls(BinomialDistributionGenerator.binomial(1, getTierProbability(lootTable)));
                        context.addPool(pool);
                    });
                }
            });
        });
    }

    // Adjust these probabilities as per rarity preference
    private static float getTierProbability(Collection<ResourceLocation> lootTable) {
        if (lootTable == TIER_ONE_SCROLL_TABLES) {
            return 0.1F;
        } else if (lootTable == TIER_TWO_SCROLL_TABLES) {
            return 0.3F;
        } else if (lootTable == TIER_THREE_SCROLL_TABLES) {
            return 0.5F;
        } else {
            return 0.6F; // Default probability if the loot table doesn't belong to any tier
        }
    }
}