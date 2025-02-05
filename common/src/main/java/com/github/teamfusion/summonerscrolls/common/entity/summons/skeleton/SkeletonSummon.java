package com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton;

import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.entity.base.goal.SummonerRangedBowAttackGoal;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;
import java.util.function.Supplier;

public class SkeletonSummon extends BaseSummonedEntity implements RangedAttackMob {

    private final SummonerRangedBowAttackGoal<SkeletonSummon> bowGoal = new SummonerRangedBowAttackGoal<>(this, 1.0, 20, 15.0f, SummonerItems.SUMMON_BOW.get());
    public final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false);

    public SkeletonSummon(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(SummonerItems.SUMMON_BOW.get()));
        this.reassessWeaponGoal();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    public void reassessWeaponGoal() {
        if (!this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, SummonerItems.SUMMON_BOW.get()));
            if (itemStack.is(SummonerItems.SUMMON_BOW.get())) {
                int i = 20;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    i = 40;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeGoal);
            }

        }
    }

    public void performRangedAttack(LivingEntity target, float velocity) {
        preformBowAttack(target, velocity, false);
    }

    public void preformBowAttack(LivingEntity target, float velocity, boolean isStray) {
        ItemStack itemStack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, SummonerItems.SUMMON_BOW.get())));
        AbstractArrow abstractArrow = this.getArrow(itemStack, velocity);
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333) - abstractArrow.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        abstractArrow.shoot(d, e + g * 0.20000000298023224, f, 1.6F, (float)(14 - this.level.getDifficulty().getId() * (isStray ? 8 : 4)));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(abstractArrow);
    }

    protected AbstractArrow getArrow(ItemStack arrowStack, float velocity) {
        return ProjectileUtil.getMobArrow(this, arrowStack, velocity);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
        return projectileWeapon == SummonerItems.SUMMON_BOW.get();
    }


    public static AttributeSupplier.Builder createSummonAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }
}