package com.github.teamfusion.summonerscrolls.common.entity.summons.enderman;

import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.google.common.base.Suppliers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.function.Supplier;

public class EndermanSummon extends BaseSummonedEntity {

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", (double)0.15f, AttributeModifier.Operation.ADDITION);

    private static final EntityDataAccessor<Boolean> DATA_CREEPY = SynchedEntityData.defineId(EndermanSummon.class, EntityDataSerializers.BOOLEAN);
    private int lastStareSound = Integer.MIN_VALUE;

    public EndermanSummon(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 1.0f;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        this.entityData.set(DATA_CREEPY, this.persistentAngerTarget != null);
        return super.getPersistentAngerTarget();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENDERMAN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {

        if (damageSource == DamageSource.OUT_OF_WORLD) {
            return super.hurt(damageSource, f);
        }

        if (!this.level.isClientSide() && this.random.nextInt(10) != 0) {
            this.teleport();
        }

        Entity directEntity = damageSource.getEntity();

        if (directEntity == this.getOwner()) {
            return false;
        }

        if (directEntity instanceof ISummon summon && summon.getOwner() == this.getOwner()) {
            return false;
        }

        return super.hurt(damageSource, f);
    }

    public static AttributeSupplier.Builder createSummonAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected boolean teleport() {
        if (this.level.isClientSide() || !this.isAlive()) {
            return false;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double e = this.getY() + (double)(this.random.nextInt(64) - 32);
        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        return this.teleport(d, e, f);
    }

    private boolean teleport(double x, double y, double z) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);
        while (mutableBlockPos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(mutableBlockPos).getMaterial().blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }
        BlockState blockState = this.level.getBlockState(mutableBlockPos);
        boolean bl = blockState.getMaterial().blocksMotion();
        boolean bl2 = blockState.getFluidState().is(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3 vec3 = this.position();
        boolean bl3 = this.randomTeleport(x, y, z, true);
        if (bl3) {
            this.level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
            if (!this.isSilent()) {
                this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0f, 1.0f);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return bl3;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (target == null) {
            this.entityData.set(DATA_CREEPY, false);
            attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.entityData.set(DATA_CREEPY, true);
            if (!attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeInstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5f, 1.0f, false);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_CREEPY.equals(key) && this.level.isClientSide) {
            this.playStareSound();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CREEPY, false);
    }

    public boolean isCreepy() {
        return this.entityData.get(DATA_CREEPY);
    }
}