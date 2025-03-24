package com.github.teamfusion.summonerscrolls.common.entity;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.entity.summons.*;
import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.ChargedCreeperSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.CreeperSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.EndermanSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.ShulkermanSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.piglin.PiglinBruteSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.piglin.PiglinSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.SkeletonSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.StraySummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.CaveSpiderSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.SpiderJockeySummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.SpiderSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.zombie.HuskSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.zombie.ZombieSummon;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import com.github.teamfusion.summonerscrolls.platform.common.MobRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SummonerEntityTypes {

    public static final CoreRegistry<EntityType<?>> ENTITY_TYPES = CoreRegistry.create(BuiltInRegistries.ENTITY_TYPE, SummonerScrolls.MOD_ID);
    public static final List<Supplier<? extends EntityType<?>>> ALL_SUMMON_ENTITIES = new ArrayList<>();

    /* Summon Entities - Tier 1 */
    public static final Supplier<EntityType<ZombieSummon>> ZOMBIE_SUMMON = register("zombie", ZombieSummon::new, 0.6f, 1.95f);
    public static final Supplier<EntityType<SpiderSummon>> SPIDER_SUMMON = register("spider", SpiderSummon::new, 1.4f, 0.9f);
    public static final Supplier<EntityType<SpiderJockeySummon>> SPIDER_JOCKEY_SUMMON = register("spider_jockey", SpiderJockeySummon::new, 1.4f, 0.9f);
    public static final Supplier<EntityType<SkeletonSummon>> SKELETON_SUMMON = register("skeleton", StraySummon::new, 0.6f, 1.99f);
    public static final Supplier<EntityType<BeeSummon>> BEE_SUMMON = register("bee", BeeSummon::new, 0.7f, 0.6f);

    /* Summon Entities - Tier 2 */
    public static final Supplier<EntityType<HuskSummon>> HUSK_SUMMON = register("husk", HuskSummon::new, 0.6f, 1.95f);
    public static final Supplier<EntityType<StraySummon>> STRAY_SUMMON = register("stray", StraySummon::new, 0.6f, 1.99f);
    public static final Supplier<EntityType<CaveSpiderSummon>> CAVE_SPIDER_SUMMON = register("cave_spider", CaveSpiderSummon::new, 0.7f, 0.5f);
    public static final Supplier<EntityType<EndermanSummon>> ENDERMAN_SUMMON = register("enderman", EndermanSummon::new, 0.6f, 2.9f);
    public static final Supplier<EntityType<PiglinSummon>> PIGLIN_SUMMON = register("piglin", PiglinSummon::new, 0.6f, 1.95f);

    /* Summon Entities - Tier 3 */
    public static final Supplier<EntityType<CreeperSummon>> CREEPER_SUMMON = register("creeper", CreeperSummon::new, 0.6f, 1.7f);
    public static final Supplier<EntityType<ChargedCreeperSummon>> CHARGED_CREEPER_SUMMON = register("charged_creeper", ChargedCreeperSummon::new, 0.6f, 1.7f);
    public static final Supplier<EntityType<PiglinBruteSummon>> PIGLIN_BRUTE_SUMMON = register("piglin_brute", PiglinBruteSummon::new, 0.6f, 1.99f);
    public static final Supplier<EntityType<ShulkermanSummon>> SHULKERMAN_SUMMON = register("shulkerman", ShulkermanSummon::new, 0.6f, 2.9f);
    public static final Supplier<EntityType<IronGolemSummon>> IRON_GOLEM_SUMMON = register("iron_golem", IronGolemSummon::new, 0.6f, 1.95f);

    public static final Supplier<EntityType<SummonerArrowEntity>> SUMMONER_ARROW = ENTITY_TYPES.register("summoner_arrow",
            () -> EntityType.Builder.of(((EntityType<SummonerArrowEntity> entityType, Level level) ->
                            new SummonerArrowEntity(entityType, level)), MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build("summoner_arrow"));

    public static void postRegister() {
        MobRegistry.attributes(ZOMBIE_SUMMON, ZombieSummon::getAttrubutes);
        MobRegistry.attributes(SPIDER_SUMMON, SpiderSummon::createSummonAttributes);
        MobRegistry.attributes(SPIDER_JOCKEY_SUMMON, SpiderJockeySummon::createSummonAttributes);
        MobRegistry.attributes(SKELETON_SUMMON, SkeletonSummon::createSummonAttributes);
        MobRegistry.attributes(BEE_SUMMON, BeeSummon::createSummonAttributes);

        MobRegistry.attributes(HUSK_SUMMON, HuskSummon::createSummonAttributes);
        MobRegistry.attributes(STRAY_SUMMON, StraySummon::createSummonAttributes);
        MobRegistry.attributes(CAVE_SPIDER_SUMMON, CaveSpiderSummon::createSummonAttributes);
        MobRegistry.attributes(ENDERMAN_SUMMON, CaveSpiderSummon::createSummonAttributes);
        MobRegistry.attributes(PIGLIN_SUMMON, PiglinSummon::createSummonAttributes);

        MobRegistry.attributes(CREEPER_SUMMON, CreeperSummon::createSummonAttributes);
        MobRegistry.attributes(CHARGED_CREEPER_SUMMON, ChargedCreeperSummon::createSummonAttributes);
        MobRegistry.attributes(PIGLIN_BRUTE_SUMMON, PiglinBruteSummon::createSummonAttributes);
        MobRegistry.attributes(SHULKERMAN_SUMMON, ShulkermanSummon::createSummonAttributes);
        MobRegistry.attributes(IRON_GOLEM_SUMMON, IronGolemSummon::createSummonAttributes);
    }

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.EntityFactory<T> factory, float width, float height) {
        Supplier<EntityType<T>> supplier = ENTITY_TYPES.register(id + "_summon", () -> EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(width, height).clientTrackingRange(8).build(id + "_summon"));

        return supplier;
    }

    public static void register() {
        ENTITY_TYPES.register();
    }
}