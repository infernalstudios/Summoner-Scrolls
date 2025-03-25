package com.github.teamfusion.summonerscrolls.mixin.client;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.entity.summons.enderman.ShulkermanSummon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBulletRenderer.class)
public class ShulkerBulletRendererMixin {

    @Shadow @Final private ShulkerBulletModel<ShulkerBullet> model;
    @Shadow @Final private static RenderType RENDER_TYPE;
    @Unique
    private static final ResourceLocation SUMMON_TEXTURE = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/projectiles/spark_summon.png");

    // More than likely a shitty way of doing this, but eh

    @Inject(method = "render(Lnet/minecraft/world/entity/projectile/ShulkerBullet;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void renderShulkerman(ShulkerBullet shulkerBullet, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (shulkerBullet.getOwner() instanceof ShulkermanSummon) {
            poseStack.pushPose();
            float h = Mth.rotLerp(g, shulkerBullet.yRotO, shulkerBullet.getYRot());
            float j = Mth.lerp(g, shulkerBullet.xRotO, shulkerBullet.getXRot());
            float k = (float)shulkerBullet.tickCount + g;
            poseStack.translate(0.0F, 0.15F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(k * 0.1F) * 180.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.cos(k * 0.1F) * 180.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(k * 0.15F) * 360.0F));
            poseStack.scale(-0.5F, -0.5F, 0.5F);
            this.model.setupAnim(shulkerBullet, 0.0F, 0.0F, 0.0F, h, j);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.beaconBeam(SUMMON_TEXTURE, true));
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.scale(1.5F, 1.5F, 1.5F);
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.entityTranslucent(SUMMON_TEXTURE));
            this.model.renderToBuffer(poseStack, vertexConsumer2, i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
            poseStack.popPose();
            ci.cancel();
        }
    }
}
