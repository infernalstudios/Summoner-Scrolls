package com.github.teamfusion.summonerscrolls.common.entity.summons.piglin;

import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.entity.base.goal.SummonerRangedCrossbowAttack;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.google.common.base.Suppliers;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.function.Supplier;

public class PiglinSummon extends BaseSummonedEntity implements CrossbowAttackMob {

    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(PiglinSummon.class, EntityDataSerializers.BOOLEAN);

    public PiglinSummon(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
        this.setItemSlot(EquipmentSlot.MAINHAND, createSpawnWeapon());

        if (getMainHandItem().is(SummonerItems.SUMMON_CROSSBOW.get())) {
            this.goalSelector.addGoal(3, new SummonerRangedCrossbowAttack<>(this, 1.0, 8.0F));
        } else {
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PIGLIN_HURT;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.PIGLIN_AMBIENT;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.setItemSlot(EquipmentSlot.MAINHAND, createSpawnWeapon());
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
    }

    public ItemStack createSpawnWeapon() {
        return (double)this.random.nextFloat() < 0.5 ? new ItemStack(SummonerItems.SUMMON_CROSSBOW.get()) : new ItemStack(SummonerItems.SUMMON_SWORD.get());
    }

    public static AttributeSupplier.Builder createSummonAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public void performCrossbowAttack(LivingEntity livingEntity, float f) {
        InteractionHand interactionHand = ProjectileUtil.getWeaponHoldingHand(livingEntity, SummonerItems.SUMMON_CROSSBOW.get());
        ItemStack itemStack = livingEntity.getItemInHand(interactionHand);
        if (livingEntity.isHolding(SummonerItems.SUMMON_CROSSBOW.get())) {
            CrossbowItem.performShooting(livingEntity.level(), livingEntity, interactionHand, itemStack, f, (float)(14 - livingEntity.level().getDifficulty().getId() * 4));
        }

        this.onCrossbowAttackPerformed();
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        this.performCrossbowAttack(this, 1.6F);
    }

    @Override
    public void setChargingCrossbow(boolean bl) {
        this.entityData.set(DATA_IS_CHARGING_CROSSBOW, bl);
    }

    private boolean isChargingCrossbow() {
        return this.entityData.get(DATA_IS_CHARGING_CROSSBOW);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    public PiglinArmPose getArmPose() {
        if (this.isAggressive() && this.isHoldingMeleeWeapon()) {
            return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
        } else if (this.isChargingCrossbow()) {
            return PiglinArmPose.CROSSBOW_CHARGE;
        } else {
            return this.isAggressive() && this.isHolding(SummonerItems.SUMMON_CROSSBOW.get()) ? PiglinArmPose.CROSSBOW_HOLD : PiglinArmPose.DEFAULT;
        }
    }

    protected boolean isHoldingMeleeWeapon() {
        return this.getMainHandItem().getItem() instanceof TieredItem;
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectile, float f) {
        this.shootCrossbowProjectile(this, livingEntity, projectile, f, 1.6F);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
        return projectileWeaponItem == SummonerItems.SUMMON_CROSSBOW.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING_CROSSBOW, false);
    }
}