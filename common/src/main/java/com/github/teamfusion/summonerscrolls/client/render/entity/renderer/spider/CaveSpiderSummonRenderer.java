package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.entity.summons.spider.CaveSpiderSummon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

//import javax.annotation.ParametersAreNonnullByDefault;

//@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Environment(EnvType.CLIENT)
public class CaveSpiderSummonRenderer<T extends CaveSpiderSummon> extends SpiderSummonRenderer<T> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/spider_summon.png");

    private static final float SCALE = 0.7F;

    public CaveSpiderSummonRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.CAVE_SPIDER);
        this.shadowRadius *= 0.7F;
    }

    @Override
    protected void scale(T summon, PoseStack poseStack, float f) {
        poseStack.scale(SCALE, SCALE, SCALE);
    }

    @Override
    public ResourceLocation getTextureLocation(T summon) {
        return SUMMON_LOCATION;
    }
}