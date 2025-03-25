package com.github.teamfusion.summonerscrolls.client.render.entity.renderer;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.CreeperSummonPowerLayer;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.CreeperSummon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CreeperSummonRenderer extends MobRenderer<CreeperSummon, CreeperModel<CreeperSummon>> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/creeper_summon.png");

    public CreeperSummonRenderer(EntityRendererProvider.Context context) {
        super(context, new CreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5f);
        this.addLayer(new CreeperSummonPowerLayer(this, context.getModelSet()));
        this.addLayer(new SummonGlowLayer<>(this, SUMMON_LOCATION));
    }


    @Override
    protected void scale(CreeperSummon livingEntity, PoseStack matrixStack, float partialTickTime) {
        float f = livingEntity.getSwelling(partialTickTime);
        float g = 1.0f + Mth.sin(f * 100.0f) * f * 0.01f;
        f = Mth.clamp(f, 0.0f, 1.0f);
        f *= f;
        f *= f;
        float h = (1.0f + f * 0.4f) * g;
        float i = (1.0f + f * 0.1f) / g;
        matrixStack.scale(h, i, h);
    }

    @Override
    protected float getWhiteOverlayProgress(CreeperSummon livingEntity, float partialTicks) {
        float f = livingEntity.getSwelling(partialTicks);
        if ((int)(f * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return Mth.clamp(f, 0.5f, 1.0f);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(CreeperSummon summon, boolean bl, boolean bl2, boolean bl3) {
        return RenderType.entityTranslucent(getTextureLocation(summon));
    }

    @Override
    protected int getBlockLightLevel(CreeperSummon summon, BlockPos blockPos) {
        return 10;
    }

    @Override
    public ResourceLocation getTextureLocation(CreeperSummon summon) {
        return SUMMON_LOCATION;
    }
}