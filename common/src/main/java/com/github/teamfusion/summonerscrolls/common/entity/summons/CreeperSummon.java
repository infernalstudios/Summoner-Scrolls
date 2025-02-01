package com.github.teamfusion.summonerscrolls.common.entity.summons;

import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.github.teamfusion.summonerscrolls.mixin.CreeperAccessor;
import com.google.common.base.Suppliers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
//@ParametersAreNonnullByDefault
public class CreeperSummon extends Creeper implements ISummon, PowerableMob, NeutralMob {
    public static final Supplier<EntityType<CreeperSummon>> TYPE = Suppliers.memoize(() -> EntityType.Builder.<CreeperSummon>of((a, b)-> new CreeperSummon(a, b, false), MobCategory.MISC).sized(0.6F, 1.7F).clientTrackingRange(8).build("creeper_summon"));
    public static final Supplier<EntityType<CreeperSummon>> TYPE_CHARGED = Suppliers.memoize(() -> EntityType.Builder.<CreeperSummon>of((a, b)-> new CreeperSummon(a, b, true), MobCategory.MISC).sized(0.6F, 1.7F).clientTrackingRange(8).build("charged_creeper_summon"));

    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(CreeperSummon.class, EntityDataSerializers.BOOLEAN);

    public static UUID ownerUUID;
    private int despawnDelay;

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(CreeperSummon.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable private UUID persistentAngerTarget;

    public CreeperSummon(EntityType<CreeperSummon> entityType, Level level, boolean isPowered) {
        super(entityType, level);
        this.entityData.set(DATA_IS_POWERED, isPowered);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    protected void registerGoals() {
        this.commonGoals(this.targetSelector, this.goalSelector);
        this.goalSelector.addGoal(2, new SwellGoal(this));
    }

    @Override
    public LivingEntity getSummon() {
        return this;
    }

    @Override
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerUUID();
            return uUID == null ? null : this.level.getPlayerByUUID(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    @Override
    public void setOwnerUUID(UUID uUID) {
        ownerUUID = uUID;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (damageSource.getEntity() == this.getOwner()) {
            return false;
        }
        if (damageSource.getEntity() instanceof ISummon summon && summon.getOwner() == this.getOwner()) {
            return false;
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public void push(Entity entity) {
        if (entity == this.getOwner()) {
            return;
        }
        super.push(entity);
    }

    @Override
    protected void doPush(Entity entity) {
        if (entity == this.getOwner()) {
            return;
        }
        super.doPush(entity);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (player.isShiftKeyDown()) {
            this.kill();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SummonerSoundEvents.SUMMON_DEATH.get();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.entityData.get(DATA_IS_POWERED)) {
            compoundTag.putBoolean("powered", true);
        }

        compoundTag.putInt("DespawnDelay", this.getDespawnDelay());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_IS_POWERED, compoundTag.getBoolean("powered"));
        if (compoundTag.contains("DespawnDelay", 99)) {
            this.setDespawnDelay(compoundTag.getInt("DespawnDelay"));
        }
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    @Override
    public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
        super.thunderHit(serverLevel, lightningBolt);
        this.entityData.set(DATA_IS_POWERED, false);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.OFFHAND);
        if (!itemstack.isEmpty()) {
            this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(SummonerItems.INVISIBLE_SUMMON_LIGHT.get()));
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.maybeDespawn();

        this.spawnSummonParticles(this.random, this.level, this.getX(), this.getRandomY(), this.getZ());
    }

    @Override
    public void setDespawnDelay(int i) {
        this.despawnDelay = i;
    }

    @Override
    public int getDespawnDelay() {
        return this.despawnDelay;
    }

    private void maybeDespawn() {
        if (this.getDespawnDelay() > 0 && --this.despawnDelay == 0) {
            this.kill();
        }
    }

    float time = 0;
    public boolean isSumoningCooldown() {
        return time >= 0;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        time = 75;
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public void tick() {
        if (this.isSumoningCooldown()) {
            time--;
            this.setDeltaMovement(0,0,0);
            this.spawnCoolParticles(this.random, this.level, this.getX(), this.getRandomY(), this.getZ());
            this.spawnSummonParticles(this.random, this.level, this.getX(), this.getRandomY(), this.getZ());
        }

        int swell = ((CreeperAccessor)this).getSwell();
        int maxSwell = ((CreeperAccessor)this).getMaxSwell();

        if (this.isAlive()) {
            ((CreeperAccessor)this).setOldSwell(swell);
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            swell += i;
            if (swell < 0) {
                swell = 0;
            }

            if (swell >= maxSwell) {
                ((CreeperAccessor)this).setOldSwell(maxSwell);
                this.explodeSummonCreeper();
            }
        }

        super.tick();
    }

    private void explodeSummonCreeper() {
        if (!this.level.isClientSide) {
            this.dead = true;
            this.level.explode(this, null, null, this.getX(), this.getY(), this.getZ(), (float)((CreeperAccessor) this).getExplosionRadius() * (this.isPowered() ? 3.0F : 1.0F), false, Explosion.BlockInteraction.NONE);
            this.discard();
            ((CreeperAccessor)this).callSpawnLingeringCloud();
        }
    }

    public static AttributeSupplier.Builder createSummonAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, i);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}