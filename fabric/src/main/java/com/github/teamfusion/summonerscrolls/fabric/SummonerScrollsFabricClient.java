package com.github.teamfusion.summonerscrolls.fabric;

import com.github.teamfusion.summonerscrolls.client.render.entity.models.PiglinSummonModel;
import com.github.teamfusion.summonerscrolls.client.render.entity.models.ZombieSummonModel;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.BeeSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.CreeperSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.IronGolemSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.SummonerArrowRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman.EndermanSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman.ShulkermanSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin.PiglinBruteSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin.PiglinSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.skeleton.SkeletonSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.skeleton.StraySummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider.CaveSpiderSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider.SpiderSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie.HuskSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie.ZombieSummonRenderer;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.platform.client.RenderRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;

public class SummonerScrollsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        //Zombies
        RenderRegistry.renderer(SummonerEntityTypes.ZOMBIE_SUMMON, ZombieSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.HUSK_SUMMON, HuskSummonRenderer::new);

        //Skeletons
        RenderRegistry.renderer(SummonerEntityTypes.SKELETON_SUMMON, SkeletonSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.STRAY_SUMMON, StraySummonRenderer::new);

        // Spiders
        RenderRegistry.renderer(SummonerEntityTypes.SPIDER_SUMMON, SpiderSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.SPIDER_JOCKEY_SUMMON, SpiderSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.CAVE_SPIDER_SUMMON, CaveSpiderSummonRenderer::new);

        // Endermen
        RenderRegistry.renderer(SummonerEntityTypes.ENDERMAN_SUMMON, EndermanSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.SHULKERMAN_SUMMON, ShulkermanSummonRenderer::new);

        // Piglins
        RenderRegistry.renderer(SummonerEntityTypes.PIGLIN_SUMMON, PiglinSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.PIGLIN_BRUTE_SUMMON, PiglinBruteSummonRenderer::new);

        // Creepers
        RenderRegistry.renderer(SummonerEntityTypes.CREEPER_SUMMON, CreeperSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.CHARGED_CREEPER_SUMMON, CreeperSummonRenderer::new);

        // Other
        RenderRegistry.renderer(SummonerEntityTypes.BEE_SUMMON, BeeSummonRenderer::new);
        RenderRegistry.renderer(SummonerEntityTypes.IRON_GOLEM_SUMMON, IronGolemSummonRenderer::new);

        RenderRegistry.renderer(SummonerEntityTypes.SUMMONER_ARROW, SummonerArrowRenderer::new);

        RenderRegistry.layerDefinition(PiglinSummonModel.LAYER_LOCATION, PiglinSummonModel.createMesh());
        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION, ZombieSummonModel.createBodyLayer());
        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION_INNER_ARMOR, ZombieSummonModel.createInnerArmorLayer());
        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION_OUTER_ARMOR, ZombieSummonModel.createOuterArmorLayer());

        FabricModelPredicateProviderRegistry.register(SummonerItems.SUMMON_BOW.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        FabricModelPredicateProviderRegistry.register(SummonerItems.SUMMON_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        FabricModelPredicateProviderRegistry.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        FabricModelPredicateProviderRegistry.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        FabricModelPredicateProviderRegistry.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, i) ->
                CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }
}
