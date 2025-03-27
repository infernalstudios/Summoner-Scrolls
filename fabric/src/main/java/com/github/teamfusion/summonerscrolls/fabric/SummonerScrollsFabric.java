package com.github.teamfusion.summonerscrolls.fabric;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class SummonerScrollsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register((player, world, hand)-> {
            SummonerEvents.useScroll(player, hand);
            return InteractionResultHolder.pass(ItemStack.EMPTY);
        });

        LootTableEvents.MODIFY.register(this::lootTableEvent);

        SummonerScrolls.commonInitialize();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(SummonerScrolls.MOD_ID, "reload_listener");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                ModConfig.register();
            }
        });
    }

    private void lootTableEvent(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id, LootTable.Builder table, LootTableSource source) {
        addToLootTables(id, table, SummonerScrolls.LootTables.OVERWORLD, 0.06f,
                BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT,
                BuiltInLootTables.STRONGHOLD_LIBRARY, BuiltInLootTables.DESERT_PYRAMID,
                BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.IGLOO_CHEST,
                BuiltInLootTables.BURIED_TREASURE, BuiltInLootTables.SHIPWRECK_TREASURE,
                BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE);

        addToLootTables(id, table, SummonerScrolls.LootTables.UPGRADE, 0.06f,
                BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT,
                BuiltInLootTables.STRONGHOLD_LIBRARY, BuiltInLootTables.DESERT_PYRAMID,
                BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.IGLOO_CHEST,
                BuiltInLootTables.BURIED_TREASURE, BuiltInLootTables.SHIPWRECK_TREASURE,
                BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, BuiltInLootTables.WOODLAND_MANSION,
                BuiltInLootTables.ANCIENT_CITY, BuiltInLootTables.RUINED_PORTAL, BuiltInLootTables.NETHER_BRIDGE,
                BuiltInLootTables.BASTION_TREASURE, BuiltInLootTables.PIGLIN_BARTERING, BuiltInLootTables.END_CITY_TREASURE);

        addToLootTables(id, table, SummonerScrolls.LootTables.RARE_OVERWORLD, 0.06f,
                BuiltInLootTables.WOODLAND_MANSION, BuiltInLootTables.ANCIENT_CITY
        );

        addToLootTables(id, table, SummonerScrolls.LootTables.NETHER, 0.06f,
                BuiltInLootTables.RUINED_PORTAL, BuiltInLootTables.NETHER_BRIDGE,
                BuiltInLootTables.BASTION_TREASURE, BuiltInLootTables.PIGLIN_BARTERING
        );

        addToLootTables(id, table, SummonerScrolls.LootTables.END, 0.06f,
                BuiltInLootTables.END_CITY_TREASURE
        );
    }

    private static void addToLootTables(ResourceLocation id, LootTable.Builder table, ResourceLocation customLootTable, float chance, ResourceLocation... vanillaTables) {

        for (ResourceLocation vanillaID : vanillaTables) {
            if (id.equals(vanillaID)) {
                LootPool pool = LootPool.lootPool()
                        .add(LootTableReference.lootTableReference(customLootTable))
                        .when(LootItemRandomChanceCondition.randomChance(chance))
                        .build();

                table.pool(pool);
            }
        }
    }
}