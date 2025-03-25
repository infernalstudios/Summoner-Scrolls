package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/world/inventory/GrindstoneMenu$4")
public abstract class GrindstoneMenuSlotMixin {

    @Unique
    private boolean addXP;
    @Unique
    private GrindstoneMenu menu;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(GrindstoneMenu grindstoneMenu, Container container, int i, int j, int k, ContainerLevelAccess containerLevelAccess, CallbackInfo ci) {
        this.addXP = false;
        this.menu = grindstoneMenu;
    }

    @Inject(method = "onTake", at = @At("HEAD"))
    private void whenScrollItemTaken(Player player, ItemStack itemStack, CallbackInfo ci) {

        Container repairSlots = ((GrindstoneAccessor) this.menu).getRepairSlots();

        ItemStack leftStack = repairSlots.getItem(0);
        ItemStack rightStack = repairSlots.getItem(1);

        CompoundTag nbtA = leftStack.getOrCreateTag();
        CompoundTag nbtB = rightStack.getOrCreateTag();

        boolean hasScroll = ScrollUtil.hasScrollProperties(nbtA) || ScrollUtil.hasScrollProperties(nbtB);
        boolean isScroll = leftStack.getItem() instanceof ScrollItem || rightStack.getItem() instanceof ScrollItem;

        if (hasScroll && !isScroll) {
            this.addXP = true;
        }
    }

    @ModifyReturnValue(method = "getExperienceAmount", at = @At("RETURN"))
    private int whenScrollItemTaken(int original) {

        if (this.addXP) {
            this.addXP = false;
            original += Math.max(1, ConfigEntries.anvilXPCost / 2);
        }

        return original;
    }
}
