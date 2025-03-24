package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.models.ZombieSummonModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;


@Environment(EnvType.CLIENT)
public class HuskSummonRenderer<T extends Mob, M extends HumanoidModel<T>> extends ZombieSummonRenderer<T, ZombieSummonModel<T>> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/husk_summon.png");

    public HuskSummonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob entity) {
        return SUMMON_LOCATION;
    }
}