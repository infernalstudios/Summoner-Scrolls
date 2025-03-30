package com.github.teamfusion.summonerscrolls.forge;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
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
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SummonerScrolls.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void layers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PiglinSummonModel.LAYER_LOCATION, PiglinSummonModel.createMesh());
        event.registerLayerDefinition(ZombieSummonModel.LAYER_LOCATION, ZombieSummonModel.createBodyLayer());
        event.registerLayerDefinition(ZombieSummonModel.LAYER_LOCATION_INNER_ARMOR, ZombieSummonModel.createInnerArmorLayer());
        event.registerLayerDefinition(ZombieSummonModel.LAYER_LOCATION_OUTER_ARMOR, ZombieSummonModel.createOuterArmorLayer());
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        SummonerScrolls.LOGGER.info("Initializing {}-CLIENT", SummonerScrolls.MOD_NAME);

        //Zombies
        event.registerEntityRenderer(SummonerEntityTypes.ZOMBIE_SUMMON.get(), ZombieSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.HUSK_SUMMON.get(), HuskSummonRenderer::new);

        //Skeletons
        event.registerEntityRenderer(SummonerEntityTypes.SKELETON_SUMMON.get(), SkeletonSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.STRAY_SUMMON.get(), StraySummonRenderer::new);

        // Spiders
        event.registerEntityRenderer(SummonerEntityTypes.SPIDER_SUMMON.get(), SpiderSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.SPIDER_JOCKEY_SUMMON.get(), SpiderSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.CAVE_SPIDER_SUMMON.get(), CaveSpiderSummonRenderer::new);

        // Endermen
        event.registerEntityRenderer(SummonerEntityTypes.ENDERMAN_SUMMON.get(), EndermanSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.SHULKERMAN_SUMMON.get(), ShulkermanSummonRenderer::new);

        // Piglins
        event.registerEntityRenderer(SummonerEntityTypes.PIGLIN_SUMMON.get(), PiglinSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.PIGLIN_BRUTE_SUMMON.get(), PiglinBruteSummonRenderer::new);

        // Creepers
        event.registerEntityRenderer(SummonerEntityTypes.CREEPER_SUMMON.get(), CreeperSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.CHARGED_CREEPER_SUMMON.get(), CreeperSummonRenderer::new);

        // Other
        event.registerEntityRenderer(SummonerEntityTypes.BEE_SUMMON.get(), BeeSummonRenderer::new);
        event.registerEntityRenderer(SummonerEntityTypes.IRON_GOLEM_SUMMON.get(), IronGolemSummonRenderer::new);

        event.registerEntityRenderer(SummonerEntityTypes.SUMMONER_ARROW.get(), SummonerArrowRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemProperties.register(SummonerItems.SUMMON_BOW.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        ItemProperties.register(SummonerItems.SUMMON_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        ItemProperties.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) {
                return 0.0f;
            } else {
                return livingEntity.getUseItem() != itemStack ? 0.0f : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f;
            }
        });

        ItemProperties.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) ->
                livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

        ItemProperties.register(SummonerItems.SUMMON_CROSSBOW.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, i) ->
                CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }
}
