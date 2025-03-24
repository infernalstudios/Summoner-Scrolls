package com.github.teamfusion.summonerscrolls.client;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.client.render.entity.models.ZombieSummonModel;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.BeeSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.SummonerArrowRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider.CaveSpiderSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.CreeperSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman.EndermanSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie.HuskSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.IronGolemSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin.PiglinBruteSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.piglin.PiglinSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.enderman.ShulkermanSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.skeleton.SkeletonSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.spider.SpiderSummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.skeleton.StraySummonRenderer;
import com.github.teamfusion.summonerscrolls.client.render.entity.renderer.zombie.ZombieSummonRenderer;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.mixin.ItemPropertiesAccessor;
import com.github.teamfusion.summonerscrolls.platform.client.RenderRegistry;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SummonerClient {
    public static void commonClientInitialize() {
        SummonerScrolls.LOGGER.info("Initializing {}-CLIENT", SummonerScrolls.MOD_NAME);

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

        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION, ZombieSummonModel.createBodyLayer());
        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION_INNER_ARMOR, ZombieSummonModel.createInnerArmorLayer());
        RenderRegistry.layerDefinition(ZombieSummonModel.LAYER_LOCATION_OUTER_ARMOR, ZombieSummonModel.createOuterArmorLayer());
    }

    public static void postClientInitialize() {
        registerProperties(SummonerItems.SUMMON_BOW, new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        registerProperties(SummonerItems.SUMMON_BOW, new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        registerProperties(SummonerItems.SUMMON_CROSSBOW, new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        registerProperties(SummonerItems.SUMMON_CROSSBOW, new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        registerProperties(SummonerItems.SUMMON_CROSSBOW, new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, i) ->
                CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }

    private static void registerProperties(Supplier<Item> item, ResourceLocation state, ClampedItemPropertyFunction function) {
        ItemPropertiesAccessor.getPROPERTIES().computeIfAbsent(item.get(), entry -> Maps.newHashMap()).put(state, function);
    }
}