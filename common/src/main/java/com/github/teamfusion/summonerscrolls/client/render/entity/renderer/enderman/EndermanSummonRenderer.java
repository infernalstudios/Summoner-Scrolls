package com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.layers.SummonGlowLayer;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.EndermanSummon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EndermanSummonRenderer<T extends EndermanSummon> extends MobRenderer<T, EndermanModel<T>> {

    public static final ResourceLocation SUMMON_LOCATION = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/summon/enderman_summon.png");
    private final RandomSource random = RandomSource.create();

    public EndermanSummonRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermanModel<>(context.bakeLayer(ModelLayers.ENDERMAN)), 0.5f);
        this.addLayer(new EnderEyesLayer<>(this));
        this.addLayer(new SummonGlowLayer<>(this, SUMMON_LOCATION));
    }

    @Override
    public Vec3 getRenderOffset(T entity, float partialTicks) {
        if (entity.isCreepy()) {
            return new Vec3(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
        }
        return super.getRenderOffset(entity, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return SUMMON_LOCATION;
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