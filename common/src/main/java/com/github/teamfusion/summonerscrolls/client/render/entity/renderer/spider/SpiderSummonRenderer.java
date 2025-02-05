package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.SpiderSummon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Spider;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SpiderSummonRenderer<T extends SpiderSummon> extends MobRenderer<T, SpiderModel<T>> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/spider_summon.png");

    public SpiderSummonRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.SPIDER);
    }

    public SpiderSummonRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, new SpiderModel<>(context.bakeLayer(layer)), 0.8f);
        this.addLayer(new SummonGlowLayer<>(this, SUMMON_LOCATION));
        this.addLayer(new SpiderEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T summon) {
        return SUMMON_LOCATION;
    }

    @Override
    protected float getFlipDegrees(T livingEntity) {
        return 180.0f;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T summon, boolean bl, boolean bl2, boolean bl3) {
        return RenderType.entityTranslucent(getTextureLocation(summon));
    }

    @Override
    protected int getBlockLightLevel(T summon, BlockPos blockPos) {
        return 10;
    }
}