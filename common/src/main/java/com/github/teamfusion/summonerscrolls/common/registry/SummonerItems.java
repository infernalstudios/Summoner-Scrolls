package com.github.teamfusion.summonerscrolls.common.registry;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.config.ConfigEntry;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.item.SummonerBowItem;
import com.github.teamfusion.summonerscrolls.common.item.SummonerTiers;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class SummonerItems {
    public static final CoreRegistry<Item> ITEMS = CoreRegistry.create(Registry.ITEM, SummonerScrolls.MOD_ID);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Mob Scrolls - Tier 1
    public static final Supplier<Item> ZOMBIE_SCROLL = regScroll("zombie", SummonerEntityTypes.ZOMBIE_SUMMON, ConfigEntries.zombie);
    public static final Supplier<Item> SPIDER_SCROLL = regScroll("spider", SummonerEntityTypes.SPIDER_SUMMON, ConfigEntries.spider);
    public static final Supplier<Item> SPIDER_JOCKEY_SCROLL = regScroll("spider_jockey", SummonerEntityTypes.SPIDER_JOCKEY_SUMMON, ConfigEntries.spiderJockey);
    public static final Supplier<Item> SKELETON_SCROLL = regScroll("skeleton", SummonerEntityTypes.SKELETON_SUMMON, ConfigEntries.skeleton);
    public static final Supplier<Item> BEE_SCROLL = regScroll("bee", 5, SummonerEntityTypes.BEE_SUMMON, ConfigEntries.bee);

    // Mob Scrolls - Tier 2
    public static final Supplier<Item> HUSK_SCROLL = regScroll("husk", SummonerEntityTypes.HUSK_SUMMON, ConfigEntries.husk);
    public static final Supplier<Item> STRAY_SCROLL = regScroll("stray", SummonerEntityTypes.STRAY_SUMMON, ConfigEntries.stray);
    public static final Supplier<Item> CAVE_SPIDER_SCROLL = regScroll("cave_spider", 3, SummonerEntityTypes.CAVE_SPIDER_SUMMON, ConfigEntries.caveSpider);
    public static final Supplier<Item> ENDERMAN_SCROLL = regScroll("enderman", SummonerEntityTypes.ENDERMAN_SUMMON, ConfigEntries.enderman);
    public static final Supplier<Item> PIGLIN_SCROLL = regScroll("piglin", SummonerEntityTypes.PIGLIN_SUMMON, ConfigEntries.piglin);

    // Mob Scrolls - Tier 3
    public static final Supplier<Item> CREEPER_SCROLL = regScroll("creeper", SummonerEntityTypes.CREEPER_SUMMON, ConfigEntries.creeper);
    public static final Supplier<Item> CHARGED_CREEPER_SCROLL = regScroll("charged_creeper", SummonerEntityTypes.CHARGED_CREEPER_SUMMON, ConfigEntries.chargedCreeper);
    public static final Supplier<Item> PIGLIN_BRUTE_SCROLL = regScroll("piglin_brute", SummonerEntityTypes.PIGLIN_BRUTE_SUMMON, ConfigEntries.piglinBrute);
    public static final Supplier<Item> SHULKERMAN_SCROLL = regScroll("shulkerman", SummonerEntityTypes.SHULKERMAN_SUMMON, ConfigEntries.shulkerman);
    public static final Supplier<Item> IRON_GOLEM_SCROLL = regScroll("iron_golem", SummonerEntityTypes.IRON_GOLEM_SUMMON, ConfigEntries.ironGolem);

    // Extra Scroll Items
    public static final Supplier<Item> ENHANCEMENT_SCROLL = ITEMS.register("enhancement_scroll", () ->
            new Item(new Item.Properties().stacksTo(16).tab(SummonerScrolls.SCROLLS_TAB)));

    //TODO: Remove Invisible Summon Light Item, doesn't do anything
    public static final Supplier<Item> INVISIBLE_SUMMON_LIGHT = ITEMS.register("invisible_summon_light", () ->
            new StandingAndWallBlockItem(Blocks.TORCH, Blocks.WALL_TORCH, new Item.Properties().stacksTo(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Summon Tools

    public static final Supplier<Item> SUMMON_BOW = ITEMS.register("summon_bow", () ->
            new SummonerBowItem(new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_CROSSBOW = ITEMS.register("summon_crossbow", () ->
            new SummonerBowItem(new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_ARROW = ITEMS.register("summon_arrow", () ->
            new ArrowItem(new Item.Properties().tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_SWORD = ITEMS.register("summon_sword", () ->
            new SwordItem(SummonerTiers.SUMMONER, 3, -2.4F, new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_AXE = ITEMS.register("summon_axe", () ->
            new AxeItem(SummonerTiers.SUMMONER, 6.0F, -3.0F, new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_PICKAXE = ITEMS.register("summon_pickaxe", () ->
            new PickaxeItem(SummonerTiers.SUMMONER, 1, -2.8F, new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_SHOVEL = ITEMS.register("summon_shovel", () ->
            new ShovelItem(SummonerTiers.SUMMONER, 1.5F, -3.0F, new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));

    public static final Supplier<Item> SUMMON_HOE = ITEMS.register("summon_hoe", () ->
            new HoeItem(SummonerTiers.SUMMONER, -2, -3.0F, new Item.Properties().stacksTo(1).tab(SummonerScrolls.SCROLLS_TAB)));




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static <T extends Entity> Supplier<Item> regScroll(String key, Supplier<EntityType<T>> entityType, ConfigEntry.Scroll scrollCfg) {
        return regScroll(key, 1, entityType, scrollCfg);
    }

    private static <T extends Entity> Supplier<Item> regScroll(String key, int entityCount, Supplier<EntityType<T>> entityType, ConfigEntry.Scroll scrollCfg) {
        return regScroll(key, entityCount, entityType, scrollCfg.xp, scrollCfg.durability);
    }


    private static <T extends Entity> Supplier<Item> regScroll(String key, int entityCount, Supplier<EntityType<T>> entityType, int xp, int damageAmount) {
        return ITEMS.register(key + "_summoner_scroll", () -> new ScrollItem(entityType, entityCount, new Item.Properties().stacksTo(1)
                .tab(SummonerScrolls.SCROLLS_TAB).durability(8), xp, damageAmount));
    }
    public static void register() {
        ITEMS.register();
    }
}