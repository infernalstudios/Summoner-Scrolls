package com.github.teamfusion.summonerscrolls.common.entity.base;

import com.github.teamfusion.summonerscrolls.client.particle.SummonerScrollsParticles;
import com.github.teamfusion.summonerscrolls.common.entity.base.goal.FollowOwnerGoal;
import com.github.teamfusion.summonerscrolls.common.entity.base.goal.OwnerHurtByTargetGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ISummon {


    @Nullable
    UUID getOwnerUUID();
    void setOwnerUUID(UUID uuid);

    @Nullable
    LivingEntity getOwner();
    LivingEntity getSummon();

    void setDespawnDelay(int i);
    int getDespawnDelay();


    default void spawnSummonParticles(RandomSource random, LevelAccessor level, double x, double y, double z) {
        spawnSummonParticles(random, level, x, y, z, 3.2f, 1.0, 0.5);
    }

    default void spawnSummonParticles(RandomSource random, LevelAccessor level, double x, double y, double z, float intensity, double intensity2, double intensity3) {
        for (float i = 0; i < Mth.TWO_PI; i += (float) (generateBoundedFloat(random, intensity) + intensity3)) {
            if (random.nextInt(3) == 1) {
                level.addParticle(SummonerScrollsParticles.SUMMON_PARTICLE.get(), x + Mth.cos(i) * intensity2, y, z + Mth.sin(i) * intensity2, 0.0, 0.0, 0.0);
            }
        }
    }

    default void spawnCoolParticles(RandomSource random, LevelAccessor level, double x, double y, double z) {
        for (float i = 0; i < Mth.TWO_PI; i += random.nextFloat() + 1) {
            level.addParticle(ParticleTypes.EXPLOSION, x + Mth.cos(i) * 0.5, y, z + Mth.sin(i) * 0.5, 0.0, 0.0, 0.0);
        }
    }

    default float generateBoundedFloat(RandomSource randomSource, float upperBound) {
        float randomFloat = randomSource.nextFloat() * upperBound;
        return (randomFloat < upperBound) ? randomFloat : Math.nextDown(upperBound);
    }


    default boolean isEnemy(LivingEntity livingEntity) {
        if (livingEntity instanceof ISummon summon) {
            if (summon.getOwner() == this.getOwner()) {
                return false;
            }
        }
        return livingEntity instanceof Enemy;
    }

    default boolean isSummonAngryAt(LivingEntity livingEntity) {
        if (!this.getSummon().canAttack(livingEntity)) {
            return false;
        }
        else if (livingEntity instanceof ISummon summon && summon.getOwner() != null && summon.getOwner().getUUID().equals(this.getOwnerUUID())) {
            return false;
        }
        else {
            return !livingEntity.getUUID().equals(this.getOwnerUUID());
        }
    }

    default void commonGoals(GoalSelector targetSelector, GoalSelector goalSelector) {
        if (this.getSummon() instanceof PathfinderMob mob) {
            targetSelector.addGoal(1, new OwnerHurtByTargetGoal(mob));
            targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(mob, Mob.class, 5, false, false, this::isEnemy));
            goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(mob, 1.0));
            targetSelector.addGoal(3, new HurtByTargetGoal(mob));
            goalSelector.addGoal(6, new FollowOwnerGoal(mob, 1.0, 10.0f, 2.0f, false));
            goalSelector.addGoal(7, new LookAtPlayerGoal(mob, Player.class, 6.0f));
            goalSelector.addGoal(8, new RandomLookAroundGoal(mob));
            targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal(mob, true));
        }
    }
}