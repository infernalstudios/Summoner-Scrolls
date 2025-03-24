package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.client.render.entity.models.ZombieSummonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;


@Environment(EnvType.CLIENT)
public class ZombieSummonRenderer<T extends Mob, M extends HumanoidModel<T>> extends HumanoidMobRenderer<T, ZombieSummonModel<T>> {

    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/zombie_summon.png");

    public ZombieSummonRenderer(EntityRendererProvider.Context context, ZombieSummonModel<T> model, ZombieSummonModel<T> innerModel, ZombieSummonModel<T> outerModel) {
        super(context, model, 0.5F);
        this.addLayer(new SummonGlowLayer<>(this, getTextureLocation(null)));
    }

    public ZombieSummonRenderer(EntityRendererProvider.Context context) {
        this(context,
                new ZombieSummonModel<>(context.bakeLayer(ZombieSummonModel.LAYER_LOCATION)), 
                new ZombieSummonModel<>(context.bakeLayer(ZombieSummonModel.LAYER_LOCATION_INNER_ARMOR)), 
                new ZombieSummonModel<>(context.bakeLayer(ZombieSummonModel.LAYER_LOCATION_OUTER_ARMOR))
        );
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return DEFAULT_TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(T livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(getTextureLocation(livingEntity));
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return 10;
    }
}