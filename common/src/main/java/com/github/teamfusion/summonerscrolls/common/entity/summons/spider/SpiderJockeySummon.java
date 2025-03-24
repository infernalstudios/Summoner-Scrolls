package com.github.teamfusion.summonerscrolls.common.entity.summons.spider;

import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.SkeletonSummon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class SpiderJockeySummon extends SpiderSummon {
    public SpiderJockeySummon(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {

        SkeletonSummon skeleton = SummonerEntityTypes.SKELETON_SUMMON.get().create(this.level());
        skeleton.setOwnerUUID(getOwnerUUID());
        skeleton.setDespawnDelay(600);
        skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0);
        skeleton.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null, null);
        skeleton.startRiding(this);

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }
}
