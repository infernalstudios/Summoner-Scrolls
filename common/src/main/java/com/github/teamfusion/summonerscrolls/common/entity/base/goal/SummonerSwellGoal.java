package com.github.teamfusion.summonerscrolls.common.entity.base.goal;

import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.CreeperSummon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SummonerSwellGoal extends Goal {
    private final CreeperSummon creeper;
    @Nullable
    private LivingEntity target;

    public SummonerSwellGoal(CreeperSummon creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = this.creeper.getTarget();
        return this.creeper.getSwellDir() > 0 || livingEntity != null && this.creeper.distanceToSqr(livingEntity) < 9.0;
    }

    @Override
    public void start() {
        this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            this.creeper.setSwellDir(-1);
            return;
        }
        if (this.creeper.distanceToSqr(this.target) > 49.0) {
            this.creeper.setSwellDir(-1);
            return;
        }
        if (!this.creeper.getSensing().hasLineOfSight(this.target)) {
            this.creeper.setSwellDir(-1);
            return;
        }
        this.creeper.setSwellDir(1);
    }
}