package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.client.render.entity.models.PiglinSummonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PiglinSummonRenderer extends HumanoidMobRenderer<Mob, PiglinSummonModel<Mob>> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/piglin_summon.png");

    public PiglinSummonRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR);
    }

    public PiglinSummonRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayerLocation, ModelLayerLocation modelLayerLocation2, ModelLayerLocation modelLayerLocation3) {
        super(context, createModel(context.getModelSet(), modelLayerLocation), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(modelLayerLocation2)), new HumanoidArmorModel(context.bakeLayer(modelLayerLocation3)), context.getModelManager()));
        this.addLayer(new SummonGlowLayer<>(this, getTextureLocation(null)));
    }

    public static PiglinSummonModel<Mob> createModel(EntityModelSet entityModelSet, ModelLayerLocation modelLayerLocation) {
        PiglinSummonModel<Mob> piglinModel = new PiglinSummonModel<>(entityModelSet.bakeLayer(modelLayerLocation));

        return piglinModel;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Mob summon, boolean bl, boolean bl2, boolean bl3) {
        return RenderType.entityTranslucent(getTextureLocation(summon));
    }

    @Override
    protected int getBlockLightLevel(Mob summon, BlockPos blockPos) {
        return 10;
    }

    @Override
    public ResourceLocation getTextureLocation(Mob summon) {
        return SUMMON_LOCATION;
    }
}