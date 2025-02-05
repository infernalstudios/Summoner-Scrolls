package com.github.teamfusion.summonerscrolls.client.render.entity.layers;

import com.github.teamfusion.summonerscrolls.common.entity.summons.creeper.CreeperSummon;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperSummonPowerLayer extends EnergySwirlLayer<CreeperSummon, CreeperModel<CreeperSummon>> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreeperModel<CreeperSummon> model;

    public CreeperSummonPowerLayer(RenderLayerParent<CreeperSummon, CreeperModel<CreeperSummon>> renderer, EntityModelSet entityModelSet) {
        super(renderer);
        this.model = new CreeperModel<>(entityModelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    @Override
    protected float xOffset(float f) {
        return f * 0.01f;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    @Override
    protected EntityModel<CreeperSummon> model() {
        return this.model;
    }
}
