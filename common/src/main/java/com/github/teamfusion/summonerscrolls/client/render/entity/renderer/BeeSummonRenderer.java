package com.github.teamfusion.summonerscrolls.client.render.entity.renderer;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BeeSummonRenderer extends BeeRenderer {
    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/bee_summon.png");

    public BeeSummonRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new SummonGlowLayer<>(this, SUMMON_LOCATION));
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Bee summon, boolean bl, boolean bl2, boolean bl3) {
        return RenderType.entityTranslucent(getTextureLocation(summon));
    }

    @Override
    protected int getBlockLightLevel(Bee summon, BlockPos blockPos) {
        return 10;
    }

    @Override
    public ResourceLocation getTextureLocation(Bee bee) {
        return SUMMON_LOCATION;
    }
}