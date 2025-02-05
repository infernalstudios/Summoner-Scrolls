package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Unique
    private ItemStack getStack() {
        return (ItemStack) (Object) this;
    }

    @Inject(method = "isEnchanted", at = @At("HEAD"), cancellable = true)
    private void glintWithScroll(CallbackInfoReturnable<Boolean> cir) {
        if (ScrollUtil.hasScrollProperties(getStack().getOrCreateTag())) {
            cir.setReturnValue(true);
        }
    }
}