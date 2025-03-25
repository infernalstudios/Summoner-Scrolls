package com.github.teamfusion.summonerscrolls.client.render.entity.renderer;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SummonerArrowRenderer<T extends AbstractArrow> extends EntityRenderer<T> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(SummonerScrolls.MOD_ID, "textures/entity/projectiles/summon_arrow.png");

    public SummonerArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(T abstractArrow, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, abstractArrow.yRotO, abstractArrow.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, abstractArrow.xRotO, abstractArrow.getXRot())));
        boolean j = false;
        float h = 0.0F;
        float k = 0.5F;
        float l = 0.0F;
        float m = 0.15625F;
        float n = 0.0F;
        float o = 0.15625F;
        float p = 0.15625F;
        float q = 0.3125F;
        float r = 0.05625F;
        float s = (float)abstractArrow.shakeTime - g;
        if (s > 0.0F) {
            float t = -Mth.sin(s * 3.0F) * s;
            poseStack.mulPose(Axis.ZP.rotationDegrees(t));
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        poseStack.scale(0.05625F, 0.05625F, 0.05625F);
        poseStack.translate(-4.0F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.beaconBeam(this.getTextureLocation(abstractArrow), true));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, i);

        for(int u = 0; u < 4; ++u) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
        }

        poseStack.popPose();
        super.render(abstractArrow, f, g, poseStack, multiBufferSource, i);
    }

    public void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int i, int j, int k, float f, float g, int l, int m, int n, int o) {

        vertexConsumer.vertex(matrix4f, (float)i, (float)j, (float)k).color(255, 255, 255, 255).uv(f, g).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(o)
                .normal(matrix3f, (float)l, (float)n, (float)m).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
