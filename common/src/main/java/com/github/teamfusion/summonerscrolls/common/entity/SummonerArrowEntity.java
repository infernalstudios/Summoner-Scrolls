package com.github.teamfusion.summonerscrolls.common.entity;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SummonerArrowEntity extends AbstractArrow {

    public SummonerArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SummonerArrowEntity(LivingEntity livingEntity, Level level) {
        super(SummonerEntityTypes.SUMMONER_ARROW.get(), livingEntity, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return SummonerItems.SUMMON_ARROW.get().getDefaultInstance();
    }
}
