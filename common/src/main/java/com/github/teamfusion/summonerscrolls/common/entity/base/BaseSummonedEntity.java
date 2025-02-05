
package com.github.teamfusion.summonerscrolls.common.entity.base;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

public abstract class BaseSummonedEntity extends PathfinderMob implements ISummon, NeutralMob {

    private UUID ownerUUID;
    private int despawnDelay;

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(BaseSummonedEntity.class, EntityDataSerializers.INT);

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @Nullable
    protected UUID persistentAngerTarget;

    public BaseSummonedEntity(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    protected void registerGoals() {
        this.commonGoals(this.targetSelector, this.goalSelector);
    }

    @Override
    public LivingEntity getSummon() {
        return this;
    }

    @Override
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    @Override
    public void setOwnerUUID(UUID uuid) {
        ownerUUID = uuid;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource == DamageSource.OUT_OF_WORLD) {
            return super.hurt(damageSource, amount);
        }

        Entity directEntity = damageSource.getEntity();
        if (directEntity == this.getOwner()) {
            return false;
        }
        if (directEntity instanceof ISummon summon && summon.getOwner() == this.getOwner()) {
            return false;
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public void push(Entity entity) {
        if (entity == this.getOwner()) {
            return;
        }
        super.push(entity);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            this.kill();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getDeathSound() {
        LocalDate today = LocalDate.now();
        boolean aprilFools = today.getMonthValue() == 4 && today.getDayOfMonth() == 1;
        return aprilFools ? SummonerSoundEvents.SUMMON_DEATH_APRIL.get() : SummonerSoundEvents.SUMMON_DEATH.get();
    }

    @Override
    protected abstract SoundEvent getHurtSound(DamageSource damageSource);

    @Nullable
    @Override
    protected abstract SoundEvent getAmbientSound();

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putInt("DespawnDelay", this.despawnDelay);

        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("DespawnDelay", 99)) {
            this.despawnDelay = tag.getInt("DespawnDelay");
        }

        UUID uuid;
        if (tag.hasUUID("Owner")) {
            uuid = tag.getUUID("Owner");
        } else {
            String s = tag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
    }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(SummonerItems.INVISIBLE_SUMMON_LIGHT.get()));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (--this.despawnDelay <= 0) {
            this.kill();
        }
        if (this.tickCount % 2 == 0) {
            this.spawnSummonParticles(this.random, this.level, this.getX(), this.getRandomY(), this.getZ());
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.discard();
    }

    @Override
    public void setDespawnDelay(int delay) {
        this.despawnDelay = Math.max(1, delay);
    }

    @Override
    public int getDespawnDelay() {
        return this.despawnDelay;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}