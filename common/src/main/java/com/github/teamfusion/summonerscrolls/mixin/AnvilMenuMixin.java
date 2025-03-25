package com.github.teamfusion.summonerscrolls.mixin;

import com.github.teamfusion.summonerscrolls.common.recipe.AnvilScrollRecipe;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Optional;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow private String itemName;

    @Shadow @Final public DataSlot cost;
    @Shadow private int repairItemCountCost;

    @Shadow public abstract int getCost();

    @Unique
    private boolean usedScrollRecipe;

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
        this.usedScrollRecipe = false;
    }

    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 1, shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void createResult(CallbackInfo ci, ItemStack stack, int i, int j, int k, ItemStack left, ItemStack right, Map<Enchantment, Integer> map) {
        AnvilMenu anvilMenu = (AnvilMenu) (Object) this;

        ItemStack leftStack = this.inputSlots.getItem(0);
        ItemStack rightStack = this.inputSlots.getItem(1);

        Container tempContainer = new SimpleContainer(leftStack, rightStack);
        Level level = this.player.level();
        RecipeManager recipeManager = level.getRecipeManager();

        Optional<AnvilScrollRecipe> scrollRecipe = recipeManager.getRecipeFor(AnvilScrollRecipe.Type.INSTANCE, tempContainer, level);

        if (scrollRecipe.isPresent()) {
            AnvilScrollRecipe recipe = scrollRecipe.get();

            ItemStack result = recipe.assemble(tempContainer, RegistryAccess.EMPTY);

            this.resultSlots.setItem(0, result);

            this.usedScrollRecipe = true;

            int nameCost = 0;
            if (this.itemName != null && !Util.isBlank(this.itemName)) {
                if (!this.itemName.equals(leftStack.getHoverName().getString())) {
                    result.setHoverName(Component.literal(this.itemName));
                }
                nameCost++;
            } else if (leftStack.hasCustomHoverName()) {
                result.resetHoverName();
            }

            anvilMenu.cost.set(Math.max(recipe.getCost() + nameCost, 0));

            this.broadcastChanges();
            ci.cancel();
        } else {
            this.usedScrollRecipe = false;
        }

        if (!ScrollUtil.applyScrollToItemInAnvil(anvilMenu, left, right, this.resultSlots, this.itemName)) {
            ci.cancel();
        }
    }

    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    private void onTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (this.usedScrollRecipe) {
            this.repairItemCountCost = 1;
        }
    }

    @ModifyReturnValue(method = "mayPickup", at = @At("RETURN"))
    private boolean allowPickup(boolean original) {
        return original || (this.usedScrollRecipe && getCost() == 0);
    }
}