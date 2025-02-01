package com.github.teamfusion.summonerscrolls.common.entity;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.entity.summons.*;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.EndermanSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.ShulkermanSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.SkeletonSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.StraySummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.CaveSpiderSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.SpiderSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.zombie.HuskSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.zombie.ZombieSummon;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import com.github.teamfusion.summonerscrolls.platform.common.MobRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SummonerEntityTypes {

    public static final CoreRegistry<EntityType<?>> ENTITY_TYPES = CoreRegistry.create(Registry.ENTITY_TYPE, SummonerScrolls.MOD_ID);
    public static final List<Supplier<? extends EntityType<?>>> ALL_SUMMON_ENTITIES = new ArrayList<>();

    /* Summon Entities - Tier 1 */
    public static final Supplier<EntityType<ZombieSummon>> ZOMBIE_SUMMON = register("zombie", ZombieSummon::new, 0.6f, 1.95f);
    public static final Supplier<EntityType<SpiderSummon>> SPIDER_SUMMON = ENTITY_TYPES.register("spider_summon", SpiderSummon.TYPE);
    public static final Supplier<EntityType<SpiderSummon>> SPIDER_JOCKEY_SUMMON = ENTITY_TYPES.register("spider_jockey_summon", SpiderSummon.TYPE_JOCKEY);
    public static final Supplier<EntityType<SkeletonSummon>> SKELETON_SUMMON = register("skeleton", StraySummon::new, 0.6f, 1.99f);
    public static final Supplier<EntityType<BeeSummon>> BEE_SUMMON = ENTITY_TYPES.register("bee_summon", BeeSummon.TYPE);

    /* Summon Entities - Tier 2 */
    public static final Supplier<EntityType<HuskSummon>> HUSK_SUMMON = register("husk", HuskSummon::new, 0.6f, 1.95f);
    public static final Supplier<EntityType<StraySummon>> STRAY_SUMMON = register("stray", StraySummon::new, 0.6f, 1.99f);
    public static final Supplier<EntityType<CaveSpiderSummon>> CAVE_SPIDER_SUMMON = ENTITY_TYPES.register("cave_spider_summon", CaveSpiderSummon.TYPE);
    public static final Supplier<EntityType<EndermanSummon>> ENDERMAN_SUMMON = ENTITY_TYPES.register("enderman_summon", EndermanSummon.TYPE);
    public static final Supplier<EntityType<PiglinSummon>> PIGLIN_SUMMON = ENTITY_TYPES.register("piglin_summon", PiglinSummon.TYPE);

    /* Summon Entities - Tier 3 */
    public static final Supplier<EntityType<CreeperSummon>> CREEPER_SUMMON = ENTITY_TYPES.register("creeper_summon", CreeperSummon.TYPE);
    public static final Supplier<EntityType<CreeperSummon>> CHARGED_CREEPER_SUMMON = ENTITY_TYPES.register("charged_creeper_summon", CreeperSummon.TYPE_CHARGED);
    public static final Supplier<EntityType<PiglinBruteSummon>> PIGLIN_BRUTE_SUMMON = ENTITY_TYPES.register("piglin_brute_summon", PiglinBruteSummon.TYPE);
    public static final Supplier<EntityType<ShulkermanSummon>> SHULKERMAN_SUMMON = ENTITY_TYPES.register("shulkerman_summoner_scroll", ShulkermanSummon.TYPE);
    public static final Supplier<EntityType<IronGolemSummon>> IRON_GOLEM_SUMMON = ENTITY_TYPES.register("iron_golem_summon", IronGolemSummon.TYPE);

    public static void postRegister() {
        MobRegistry.attributes(ZOMBIE_SUMMON, ZombieSummon::getAttrubutes);
        MobRegistry.attributes(SPIDER_SUMMON, SpiderSummon::createSummonAttributes);
        MobRegistry.attributes(SPIDER_JOCKEY_SUMMON, SpiderSummon::createSummonAttributes);
        MobRegistry.attributes(SKELETON_SUMMON, SkeletonSummon::createSummonAttributes);
        MobRegistry.attributes(BEE_SUMMON, BeeSummon::createSummonAttributes);

        MobRegistry.attributes(HUSK_SUMMON, HuskSummon::createSummonAttributes);
        MobRegistry.attributes(STRAY_SUMMON, StraySummon::createSummonAttributes);
        MobRegistry.attributes(CAVE_SPIDER_SUMMON, CaveSpiderSummon::createSummonAttributes);
        MobRegistry.attributes(ENDERMAN_SUMMON, CaveSpiderSummon::createSummonAttributes);
        MobRegistry.attributes(PIGLIN_SUMMON, PiglinSummon::createSummonAttributes);

        MobRegistry.attributes(CREEPER_SUMMON, CreeperSummon::createSummonAttributes);
        MobRegistry.attributes(CHARGED_CREEPER_SUMMON, CreeperSummon::createSummonAttributes);
        MobRegistry.attributes(PIGLIN_BRUTE_SUMMON, PiglinBruteSummon::createSummonAttributes);
        MobRegistry.attributes(SHULKERMAN_SUMMON, ShulkermanSummon::createSummonAttributes);
        MobRegistry.attributes(IRON_GOLEM_SUMMON, IronGolemSummon::createSummonAttributes);
    }

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, float width, float height) {
        Supplier<EntityType<T>> supplier = ENTITY_TYPES.register(id + "_summon", () -> EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(width, height).clientTrackingRange(8).build(id + "_summon"));

        return supplier;
    }
}