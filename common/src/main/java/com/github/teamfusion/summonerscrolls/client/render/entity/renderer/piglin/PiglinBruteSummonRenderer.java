package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;


@Environment(EnvType.CLIENT)
public class PiglinBruteSummonRenderer extends PiglinSummonRenderer {

    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/piglin_brute_summon.png");

    public PiglinBruteSummonRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.PIGLIN_BRUTE, ModelLayers.PIGLIN_BRUTE_INNER_ARMOR, ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob summon) {
        return SUMMON_LOCATION;
    }
}