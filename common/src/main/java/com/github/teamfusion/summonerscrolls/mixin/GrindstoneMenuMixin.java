package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrindstoneMenu.class)
public abstract class GrindstoneMenuMixin extends AbstractContainerMenu {

    @Shadow @Final private Container repairSlots;

    @Shadow @Final private Container resultSlots;

    protected GrindstoneMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void checkForScroll(CallbackInfo ci) {

        ItemStack leftStack = this.repairSlots.getItem(0);
        ItemStack rightStack = this.repairSlots.getItem(1);

        boolean isScroll = leftStack.getItem() instanceof ScrollItem || rightStack.getItem() instanceof ScrollItem;

        if (isScroll) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.broadcastChanges();
            ci.cancel();
        }
    }


    @ModifyReturnValue(method = "removeNonCurses", at = @At(value = "RETURN"))
    private ItemStack createResult(ItemStack original) {
        CompoundTag nbt = original.getOrCreateTag();

        if (ScrollUtil.hasScrollProperties(nbt) && !(original.getItem() instanceof ScrollItem)) {
            ScrollUtil.removeScrollFromItem(original);
        }

        return original;
    }
}
