package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.entity.base.BaseSummonedEntity;
import com.github.teamfusion.summonerscrolls.common.util.methodHolders.SummonerPlayerMixinHolder;
import com.github.teamfusion.summonerscrolls.common.util.cooldowns.SummonerItemCooldowns;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements SummonerPlayerMixinHolder {
    @Unique
    private SummonerItemCooldowns summonerscrolls$summonerCooldowns;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {

        Player player = (Player) (Object) this;

        if (damageSource.is(DamageTypes.EXPLOSION) && damageSource.getEntity() instanceof BaseSummonedEntity creeperSummon) {
            if (creeperSummon.getOwner() == player) {
                cir.setReturnValue(false);
            }
        }
    }

    //Injecting Custom Cooldowns.
    //This is overriding a methodHolder interface's method in order to ensure the method can be used elsewhere.
    @Override
    @Unique
    public SummonerItemCooldowns summonerscrolls$getSummonerCooldowns() {
        return this.summonerscrolls$summonerCooldowns;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void onInit(Level level, BlockPos blockPos, float f, GameProfile gameProfile, CallbackInfo ci) {
        this.summonerscrolls$summonerCooldowns = SummonerItemCooldowns.createSummonerItemCooldowns();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        this.summonerscrolls$summonerCooldowns.tick();
    }
}