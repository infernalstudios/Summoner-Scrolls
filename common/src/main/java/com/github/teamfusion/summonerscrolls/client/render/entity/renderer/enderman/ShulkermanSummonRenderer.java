package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.ShulkermanSummon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;

//@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Environment(EnvType.CLIENT)
public class ShulkermanSummonRenderer extends EndermanSummonRenderer<ShulkermanSummon> {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/shulkerman_summon.png");

    public ShulkermanSummonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ShulkermanSummon summon) {
        return SUMMON_LOCATION;
    }
}