package com.github.teamfusion.summonerscrolls.platform.common.forge;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.platform.common.LootRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = SummonerScrolls.MOD_ID)
public class LootRegistryImpl {

    private static final Set<Consumer<LootTableLoadEvent>> MODIFICATIONS = ConcurrentHashMap.newKeySet();

    @SubscribeEvent
    public static void onModify(LootTableLoadEvent event) {
        MODIFICATIONS.forEach(consumer -> consumer.accept(event));
    }

    public static void modify(LootRegistry.LootTableModifier modifier) {
//        MODIFICATIONS.add(event -> {
//            LootRegistry.LootTableContext context = new LootRegistry.LootTableContext() {
//                @Override
//                public void addPool(LootPool pool) {
//                    event.getTable().addPool(pool);
//                }
//
//                @Override
//                public void addPool(LootPool.Builder poolBuilder) {
//                    event.getTable().addPool(poolBuilder.build());
//                }
//            };
//            ResourceLocation id = event.getName();
//
//            boolean isBuiltin = "minecraft".equals(id.getNamespace());
//
//            modifier.modify(null, id, context, isBuiltin);
//        });
    }
}