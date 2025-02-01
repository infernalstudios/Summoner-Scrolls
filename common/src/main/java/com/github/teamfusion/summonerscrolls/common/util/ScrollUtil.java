package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerEnchantments;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
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
            .put(SummonerEntityTypes.ZOMBIE_SUMMON.get(), SummonerEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.SPIDER_SUMMON.get(), SummonerEnchantments.SPIDER_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.SPIDER_JOCKEY_SUMMON.get(), SummonerEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.SKELETON_SUMMON.get(), SummonerEnchantments.SKELETON_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.BEE_SUMMON.get(), SummonerEnchantments.BEE_SCROLL_ENCHANTMENT.get())

            /* Summon Types - Tier 2 */
            .put(SummonerEntityTypes.HUSK_SUMMON.get(), SummonerEnchantments.HUSK_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.STRAY_SUMMON.get(), SummonerEnchantments.STRAY_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.CAVE_SPIDER_SUMMON.get(), SummonerEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.ENDERMAN_SUMMON.get(), SummonerEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.PIGLIN_SUMMON.get(), SummonerEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get())

            /* Summon Types - Tier 3 */
            .put(SummonerEntityTypes.CREEPER_SUMMON.get(), SummonerEnchantments.CREEPER_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.CHARGED_CREEPER_SUMMON.get(), SummonerEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.PIGLIN_BRUTE_SUMMON.get(), SummonerEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.SHULKERMAN_SUMMON.get(), SummonerEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get())
            .put(SummonerEntityTypes.IRON_GOLEM_SUMMON.get(), SummonerEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get())
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
            if (enchantment == SummonerEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.zombie.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spider.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spiderJockey.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.skeleton.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.bee.xp;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.husk.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.stray.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.caveSpider.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.enderman.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglin.xp;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.creeper.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglinBrute.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.shulkerman.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.ironGolem.xp;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.chargedCreeper.xp;
            }
        }

        return 0;
    }

    public static int getScrollXPCount(ItemStack stack) {
        Item item = stack.getItem();

        /* Summon XP - Tier 1 */
        if (item == SummonerItems.ZOMBIE_SCROLL.get()){
            return ConfigEntries.zombie.xp;
        }
        if (item == SummonerItems.SPIDER_SCROLL.get()){
            return ConfigEntries.spider.xp;
        }
        if (item == SummonerItems.SPIDER_JOCKEY_SCROLL.get()){
            return ConfigEntries.spiderJockey.xp;
        }
        if (item == SummonerItems.SKELETON_SCROLL.get()){
            return ConfigEntries.skeleton.xp;
        }
        if (item == SummonerItems.BEE_SCROLL.get()){
            return ConfigEntries.bee.xp;
        }

        /* Summon Types - Tier 2 */
        if (item == SummonerItems.HUSK_SCROLL.get()){
            return ConfigEntries.husk.xp;
        }
        if (item == SummonerItems.STRAY_SCROLL.get()){
            return ConfigEntries.stray.xp;
        }
        if (item == SummonerItems.CAVE_SPIDER_SCROLL.get()){
            return ConfigEntries.caveSpider.xp;
        }
        if (item == SummonerItems.ENDERMAN_SCROLL.get()){
            return ConfigEntries.enderman.xp;
        }
        if (item == SummonerItems.PIGLIN_SCROLL.get()){
            return ConfigEntries.piglin.xp;
        }

        /* Summon Types - Tier 3 */
        if (item == SummonerItems.CREEPER_SCROLL.get()){
            return ConfigEntries.creeper.xp;
        }
        if (item == SummonerItems.PIGLIN_BRUTE_SCROLL.get()){
            return ConfigEntries.piglinBrute.xp;
        }
        if (item == SummonerItems.SHULKERMAN_SCROLL.get()){
            return ConfigEntries.shulkerman.xp;
        }
        if (item == SummonerItems.IRON_GOLEM_SCROLL.get()){
            return ConfigEntries.ironGolem.xp;
        }
        if (item == SummonerItems.CHARGED_CREEPER_SCROLL.get()){
            return ConfigEntries.chargedCreeper.xp;
        }

        return 0;
    }

    public static int getDurability(ItemStack stack) {
        /* Summon XP - Tier 1 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.zombie.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spider.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.spiderJockey.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SKELETON_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.skeleton.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.BEE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.bee.durability;
            }
        }

        /* Summon Types - Tier 2 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.HUSK_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.husk.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.STRAY_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.stray.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.caveSpider.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.enderman.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglin.durability;
            }
        }

        /* Summon Types - Tier 3 */
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.creeper.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.piglinBrute.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.shulkerman.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.ironGolem.durability;
            }
        }
        for(Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            if (enchantment == SummonerEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get()){
                return ConfigEntries.chargedCreeper.durability;
            }
        }

        return 0;
    }
}