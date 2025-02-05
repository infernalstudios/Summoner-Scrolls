package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.CreeperSummon;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (damageSource.isExplosion() && damageSource.getEntity() instanceof CreeperSummon creeperSummon) {
            if (creeperSummon.getOwner() == this)
                cir.setReturnValue(false);
        }
    }
}