package com.github.teamfusion.summonerscrolls.mixin;

import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Creeper.class)
public interface CreeperAccessor {
    @Accessor
    int getSwell();

    @Accessor
    int getMaxSwell();

    @Accessor
    int getExplosionRadius();

    @Accessor
    void setOldSwell(int oldSwell);

    @Invoker
    void callSpawnLingeringCloud();
}