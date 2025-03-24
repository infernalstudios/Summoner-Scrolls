package com.github.teamfusion.summonerscrolls.common.entity.base.goal;

import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class OwnerHurtByTargetGoal extends TargetGoal {
    private final Mob summon;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public OwnerHurtByTargetGoal(Mob summon) {
        super(summon, false);
        this.summon = summon;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity livingEntity = null;
        if (summon instanceof ISummon summon1) {
            livingEntity = summon1.getOwner();
        }

        if (livingEntity == null) {
            return false;
        } else {
            this.ownerLastHurtBy = livingEntity.getLastHurtByMob();
            int i = livingEntity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        LivingEntity livingEntity = null;
        if (summon instanceof ISummon summon1) {
            livingEntity = summon1.getOwner();
        }

        this.summon.setTarget(this.ownerLastHurtBy);
        if (livingEntity != null) {
            this.timestamp = livingEntity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}