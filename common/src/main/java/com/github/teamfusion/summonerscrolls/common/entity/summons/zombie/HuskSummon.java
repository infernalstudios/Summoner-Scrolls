package com.github.teamfusion.summonerscrolls.common.entity.summons.zombie;

import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;


public class HuskSummon extends ZombieSummon {

    public HuskSummon(EntityType<? extends BaseSummonedEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createSummonAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean bl = super.doHurtTarget(entity);
        if (entity instanceof LivingEntity livingEntity) {
            if (bl && this.getMainHandItem().isEmpty()) {
                float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
                livingEntity.setSecondsOnFire(8);
            }
        }

        return bl;
    }
}