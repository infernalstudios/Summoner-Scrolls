package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.skeleton;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonStrayClothingLayer;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.SkeletonSummon;
import com.github.teamfusion.summonerscrolls.common.entity.summons.skeleton.StraySummon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class StraySummonRenderer extends SkeletonSummonRenderer<StraySummon> {

    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/stray_summon.png");

    public StraySummonRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new SummonStrayClothingLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(StraySummon entity) {
        return super.getTextureLocation(entity);
    }
}