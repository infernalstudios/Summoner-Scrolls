package com.github.teamfusion.summonerscrolls.client.render.entity.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SummonGlowLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private final ResourceLocation SUMMON_NAME;

    public SummonGlowLayer(RenderLayerParent<T, M> renderLayerParent, ResourceLocation summonName) {
        super(renderLayerParent);
        this.SUMMON_NAME = new ResourceLocation(summonName.toString().replace(".png", "_glow.png"));
    }

    @Override
    public @NotNull RenderType renderType() {
        return RenderType.eyes(SUMMON_NAME);
    }
}