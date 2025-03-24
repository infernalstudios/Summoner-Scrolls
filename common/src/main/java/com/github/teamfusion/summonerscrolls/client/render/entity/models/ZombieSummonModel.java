package com.github.teamfusion.summonerscrolls.client.render.entity.models;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import java.util.function.Supplier;

public class ZombieSummonModel<T extends Mob> extends HumanoidModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SummonerScrolls.MOD_ID, "zombie"), "main");
    public static final ModelLayerLocation LAYER_LOCATION_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation(SummonerScrolls.MOD_ID, "zombie_inner_armor"), "main");
    public static final ModelLayerLocation LAYER_LOCATION_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation(SummonerScrolls.MOD_ID, "zombie_outer_armor"), "main");

    public ZombieSummonModel(ModelPart root) {
        super(root);
    }

    public static Supplier<LayerDefinition> createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        return () -> LayerDefinition.create(mesh, 64, 64);
    }

    public static Supplier<LayerDefinition> createInnerArmorLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(1.0F), 0.5F);
        return () -> LayerDefinition.create(mesh, 64, 32);
    }

    public static Supplier<LayerDefinition> createOuterArmorLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.5F);
        return () -> LayerDefinition.create(mesh, 64, 32);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, true, this.attackTime, ageInTicks);
    }
}
