package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
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
                return ConfigEntries.zombie.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spider.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spiderJockey.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.skeleton.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.bee.xp;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.husk.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.stray.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.caveSpider.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.enderman.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglin.xp;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.creeper.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglinBrute.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.shulkerman.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.ironGolem.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.chargedCreeper.xp;
            }
        }

        return 0;
    }

    public static int getScrollXPCount(ItemStack stack) {
        Item item = stack.getItem();

        /* Summon XP - Tier 1 */
        if (item == SSItems.ZOMBIE_SCROLL.get()){
            return ConfigEntries.zombie.xp;
        }
        if (item == SSItems.SPIDER_SCROLL.get()){
            return ConfigEntries.spider.xp;
        }
        if (item == SSItems.SPIDER_JOCKEY_SCROLL.get()){
            return ConfigEntries.spiderJockey.xp;
        }
        if (item == SSItems.SKELETON_SCROLL.get()){
            return ConfigEntries.skeleton.xp;
        }
        if (item == SSItems.BEE_SCROLL.get()){
            return ConfigEntries.bee.xp;
        }

        /* Summon Types - Tier 2 */
        if (item == SSItems.HUSK_SCROLL.get()){
            return ConfigEntries.husk.xp;
        }
        if (item == SSItems.STRAY_SCROLL.get()){
            return ConfigEntries.stray.xp;
        }
        if (item == SSItems.CAVE_SPIDER_SCROLL.get()){
            return ConfigEntries.caveSpider.xp;
        }
        if (item == SSItems.ENDERMAN_SCROLL.get()){
            return ConfigEntries.enderman.xp;
        }
        if (item == SSItems.PIGLIN_SCROLL.get()){
            return ConfigEntries.piglin.xp;
        }

        /* Summon Types - Tier 3 */
        if (item == SSItems.CREEPER_SCROLL.get()){
            return ConfigEntries.creeper.xp;
        }
        if (item == SSItems.PIGLIN_BRUTE_SCROLL.get()){
            return ConfigEntries.piglinBrute.xp;
        }
        if (item == SSItems.SHULKERMAN_SCROLL.get()){
            return ConfigEntries.shulkerman.xp;
        }
        if (item == SSItems.IRON_GOLEM_SCROLL.get()){
            return ConfigEntries.ironGolem.xp;
        }
        if (item == SSItems.CHARGED_CREEPER_SCROLL.get()){
            return ConfigEntries.chargedCreeper.xp;
        }

        return 0;
    }

    public static int getDurability(ItemStack stack) {
        /* Summon XP - Tier 1 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.zombie.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spider.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spiderJockey.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.skeleton.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.bee.durability;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.husk.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.stray.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.caveSpider.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.enderman.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglin.durability;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.creeper.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglinBrute.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.shulkerman.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.ironGolem.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.chargedCreeper.durability;
            }
        }

        return 0;
    }
}