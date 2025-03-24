package com.github.teamfusion.summonerscrolls.platform.common;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;

public class LootRegistry {
    @ExpectPlatform
    public static void modify(LootTableModifier modifier) {
        throw new AssertionError();
    }

    public interface LootTableModifier {
        void modify(LootDataManager tables, ResourceLocation path, LootTableContext context, boolean builtin);
    }

    public interface LootTableContext {
        void addPool(LootPool pool);

        default void addPool(LootPool.Builder pool) {
            this.addPool(pool.build());
        }
    }
}