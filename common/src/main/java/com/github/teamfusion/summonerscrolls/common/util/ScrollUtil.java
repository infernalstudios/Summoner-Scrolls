package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ScrollUtil {

    public static EntityType<?> getEntityType(CompoundTag nbt) {
        return Registry.ENTITY_TYPE.get(new ResourceLocation(nbt.getString("summon")));
    }

    public static int getEntityCount(CompoundTag nbt) {
        return nbt.getInt("count");
    }

    public static int getCost(CompoundTag nbt) {
        return nbt.getInt("cost");
    }

    public static int getDamage(CompoundTag nbt) {
        return nbt.getInt("damageAmount");
    }

    public static boolean hasScrollProperties(CompoundTag nbt) {
        return nbt.contains("summon") && nbt.contains("count") && nbt.contains("cost") && nbt.contains("damageAmount");
    }
}