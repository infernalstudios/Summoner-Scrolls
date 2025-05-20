package com.github.teamfusion.summonerscrolls.mixin.client;

import com.github.teamfusion.summonerscrolls.common.util.methodHolders.SummonerGuiGraphicsMixinHolder;
import com.github.teamfusion.summonerscrolls.common.util.methodHolders.SummonerPlayerMixinHolder;
import com.github.teamfusion.summonerscrolls.common.util.cooldowns.SummonerItemCooldowns;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements SummonerGuiGraphicsMixinHolder {

    //Shadowed fields and classes from the original class
    @Shadow
    private Minecraft minecraft;

    @Shadow
    public abstract void fill(RenderType renderType, int i, int j, int k, int l, int m);

    //Injections
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("TAIL"))
    private void renderItemDecorations(Font font, ItemStack itemStack, int i, int j, @Nullable String string, CallbackInfo ci) {

        LocalPlayer localPlayer = this.minecraft.player;
        if (localPlayer == null) return;

        float f = localPlayer.getCooldowns().getCooldownPercent(itemStack.getItem(), this.minecraft.getFrameTime());
        if (f != 0.0F) return;

        SummonerItemCooldowns summonerCooldowns = ((SummonerPlayerMixinHolder)localPlayer).summonerscrolls$getSummonerCooldowns();
        float p = summonerCooldowns.getCooldownPercent(itemStack, 0.0F);
        if (p <= 0.0F) return;

        int m = j + Mth.floor(16.0F * (1.0F - p));
        int n = m + Mth.ceil(16.0F * p);
        this.fill(RenderType.guiOverlay(), i, m, i + 16, n, Integer.MAX_VALUE);
    }
}
