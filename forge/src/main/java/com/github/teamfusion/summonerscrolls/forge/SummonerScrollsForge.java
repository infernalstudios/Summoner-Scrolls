package com.github.teamfusion.summonerscrolls.forge;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(SummonerScrolls.MOD_ID)
public class SummonerScrollsForge {
    public SummonerScrollsForge() {
        SummonerScrolls.commonInitialize();

        MinecraftForge.EVENT_BUS.register(CommonEvents.class);
        MinecraftForge.EVENT_BUS.addListener(SummonerScrollsForge::addReloadListener);
    }

    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener((ResourceManagerReloadListener) resourceManager -> ModConfig.register());
    }


    @Mod.EventBusSubscriber(modid = SummonerScrolls.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class SummonerLootTable {
        @SubscribeEvent
        public static void lootTableEvent(LootTableLoadEvent event) {
            addToLootTables(event, SummonerScrolls.LootTables.OVERWORLD, 0.06f,
                    BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT,
                    BuiltInLootTables.STRONGHOLD_LIBRARY, BuiltInLootTables.DESERT_PYRAMID,
                    BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.IGLOO_CHEST,
                    BuiltInLootTables.BURIED_TREASURE, BuiltInLootTables.SHIPWRECK_TREASURE,
                    BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE);

            addToLootTables(event, SummonerScrolls.LootTables.UPGRADE, 0.06f,
                    BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.ABANDONED_MINESHAFT,
                    BuiltInLootTables.STRONGHOLD_LIBRARY, BuiltInLootTables.DESERT_PYRAMID,
                    BuiltInLootTables.JUNGLE_TEMPLE, BuiltInLootTables.IGLOO_CHEST,
                    BuiltInLootTables.BURIED_TREASURE, BuiltInLootTables.SHIPWRECK_TREASURE,
                    BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, BuiltInLootTables.WOODLAND_MANSION,
                    BuiltInLootTables.ANCIENT_CITY, BuiltInLootTables.RUINED_PORTAL, BuiltInLootTables.NETHER_BRIDGE,
                    BuiltInLootTables.BASTION_TREASURE, BuiltInLootTables.PIGLIN_BARTERING, BuiltInLootTables.END_CITY_TREASURE);

            addToLootTables(event, SummonerScrolls.LootTables.RARE_OVERWORLD, 0.06f,
                    BuiltInLootTables.WOODLAND_MANSION, BuiltInLootTables.ANCIENT_CITY
            );

            addToLootTables(event, SummonerScrolls.LootTables.NETHER, 0.06f,
                    BuiltInLootTables.RUINED_PORTAL, BuiltInLootTables.NETHER_BRIDGE,
                    BuiltInLootTables.BASTION_TREASURE, BuiltInLootTables.PIGLIN_BARTERING
            );

            addToLootTables(event, SummonerScrolls.LootTables.END, 0.06f,
                    BuiltInLootTables.END_CITY_TREASURE
            );

            addToLootTables(event, SummonerScrolls.LootTables.END, 0.06f,
                    BuiltInLootTables.WOODLAND_MANSION, BuiltInLootTables.ANCIENT_CITY,
                    BuiltInLootTables.ANCIENT_CITY_ICE_BOX, BuiltInLootTables.END_CITY_TREASURE
            );
        }

        private static void addToLootTables(LootTableLoadEvent event, ResourceLocation lootTableId, float chance, ResourceLocation... vanillaTables) {
            for (ResourceLocation id : vanillaTables) {
                if (event.getName().equals(id)) {
                    event.getTable().addPool(
                            LootPool.lootPool()
                                    .name(SummonerScrolls.MOD_ID + ":" + id.getPath() + lootTableId.getPath())
                                    .add(LootTableReference.lootTableReference(lootTableId))
                                    .when(LootItemRandomChanceCondition.randomChance(chance))
                                    .build()
                    );
                }
            }
        }
    }
}