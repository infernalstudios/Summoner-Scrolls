package com.github.teamfusion.summonerscrolls.common.entity.summons.spider;

import com.google.common.base.Suppliers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
//@ParametersAreNonnullByDefault
public class CaveSpiderSummon extends SpiderSummon {
    public static final Supplier<EntityType<CaveSpiderSummon>> TYPE = Suppliers.memoize(() -> EntityType.Builder.of(CaveSpiderSummon::new, MobCategory.MISC).sized(0.7F, 0.5F).clientTrackingRange(8).build("cave_spider_summon"));

    public CaveSpiderSummon(EntityType<? extends CaveSpiderSummon> summon, Level level) {
        super(summon, level, false);
    }

    @Override
    public boolean doHurtTarget(Entity summon) {
        if (super.doHurtTarget(summon)) {
            if (summon instanceof LivingEntity) {
                int i = 0;
                if (this.level.getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.level.getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 0) {
                    ((LivingEntity)summon).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, i * 20, 0), this);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        return spawnGroupData;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.45F;
    }

    public static AttributeSupplier.Builder createSummonAttributes() {
        return SpiderSummon.createSummonAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.45);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }
}