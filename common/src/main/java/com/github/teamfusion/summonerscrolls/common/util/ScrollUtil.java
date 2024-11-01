package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.registry.SSEnchantments;
import com.github.teamfusion.summonerscrolls.common.registry.SSEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SSItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class ScrollUtil {
    private static final Map<EntityType<? extends LivingEntity>, Enchantment> SCROLL_SUMMONEABLES = ImmutableMap.<EntityType<? extends LivingEntity>, Enchantment>builder()
            /* Summon Types - Tier 1 */
            .put(SSEntityTypes.ZOMBIE_SUMMON.get(), SSEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.SPIDER_SUMMON.get(), SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.SPIDER_JOCKEY_SUMMON.get(), SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.SKELETON_SUMMON.get(), SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.BEE_SUMMON.get(), SSEnchantments.BEE_SCROLL_ENCHANTMENT.get())

            /* Summon Types - Tier 2 */
            .put(SSEntityTypes.HUSK_SUMMON.get(), SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.STRAY_SUMMON.get(), SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.CAVE_SPIDER_SUMMON.get(), SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.ENDERMAN_SUMMON.get(), SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.PIGLIN_SUMMON.get(), SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get())

            /* Summon Types - Tier 3 */
            .put(SSEntityTypes.CREEPER_SUMMON.get(), SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.CHARGED_CREEPER_SUMMON.get(), SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.PIGLIN_BRUTE_SUMMON.get(), SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.SHULKERMAN_SUMMON.get(), SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get())
            .put(SSEntityTypes.IRON_GOLEM_SUMMON.get(), SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get())
            .build();

    private static EntityType<?> TYPE;

    public static EntityType<?> getEntityType(ItemStack stack) {
        SCROLL_SUMMONEABLES.forEach((summon, scroll) -> {
            for (Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
                if (enchantment == scroll) {
                    TYPE = summon;
                    break;
                }
            }
        });

        return TYPE;
    }

    //TODO: XP wont work for all
    public static int getXP(ItemStack stack) {
        /* Summon XP - Tier 1 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return 15;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return 15;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return 20;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return 20;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return 15;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return 30;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return 30;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return 40;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return 50;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return 40;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return 50;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return 60;
            }
        }

        return 0;
    }

    public static int getScrollXPCount(ItemStack stack) {
        Item item = stack.getItem();

        /* Summon XP - Tier 1 */
        if (item == SSItems.ZOMBIE_SCROLL.get()){
            return 10;
        }
        if (item == SSItems.SPIDER_SCROLL.get()){
            return 10;
        }
        if (item == SSItems.SPIDER_JOCKEY_SCROLL.get()){
            return 10;
        }
        if (item == SSItems.SKELETON_SCROLL.get()){
            return 15;
        }
        if (item == SSItems.BEE_SCROLL.get()){
            return 15;
        }

        /* Summon Types - Tier 2 */
        if (item == SSItems.HUSK_SCROLL.get()){
            return 20;
        }
        if (item == SSItems.STRAY_SCROLL.get()){
            return 20;
        }
        if (item == SSItems.CAVE_SPIDER_SCROLL.get()){
            return 15;
        }
        if (item == SSItems.ENDERMAN_SCROLL.get()){
            return 30;
        }
        if (item == SSItems.PIGLIN_SCROLL.get()){
            return 30;
        }

        /* Summon Types - Tier 3 */
        if (item == SSItems.CREEPER_SCROLL.get()){
            return 40;
        }
        if (item == SSItems.PIGLIN_BRUTE_SCROLL.get()){
            return 50;
        }
        if (item == SSItems.SHULKERMAN_SCROLL.get()){
            return 40;
        }
        if (item == SSItems.IRON_GOLEM_SCROLL.get()){
            return 50;
        }
        if (item == SSItems.CHARGED_CREEPER_SCROLL.get()){
            return 60;
        }

        return 0;
    }

    public static int getDurability(ItemStack stack) {
        /* Summon XP - Tier 1 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get()){
                return 1;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return 1;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return 1;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return 2;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return 5;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return 5;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return 5;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return 5;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return 5;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return 10;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return 15;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return 15;
            }
        }

        return 0;
    }
}