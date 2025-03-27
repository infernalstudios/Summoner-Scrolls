package com.github.teamfusion.summonerscrolls;

import com.github.teamfusion.summonerscrolls.client.SummonerClient;
import com.github.teamfusion.summonerscrolls.client.particle.SummonerScrollsParticles;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerRecipes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerCreativeTab;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.github.teamfusion.summonerscrolls.common.util.trade.SummonerTrades;
import com.github.teamfusion.summonerscrolls.platform.ModInstance;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummonerScrolls {
    public static final String MOD_ID = "summonerscrolls";
    public static final String MOD_NAME = "Summoner Scrolls";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID).common(ModConfig::register).build();


    public static void commonInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        INSTANCE.bootstrap();

        SummonerItems.register();
        SummonerEntityTypes.register();
        SummonerSoundEvents.SOUND_EVENTS.register();
        SummonerCreativeTab.TABS.register();

        SummonerEntityTypes.postRegister();

        SummonerScrollsParticles.init();
        SummonerTrades.init();
        SummonerRecipes.registerRecipes();
    }

    public static class LootTables {
        public static final ResourceLocation OVERWORLD = new ResourceLocation(SummonerScrolls.MOD_ID, "chests/overworld_scrolls");
        public static final ResourceLocation RARE_OVERWORLD = new ResourceLocation(SummonerScrolls.MOD_ID, "chests/rare_overworld_scrolls");
        public static final ResourceLocation NETHER = new ResourceLocation(SummonerScrolls.MOD_ID, "chests/nether_scrolls");
        public static final ResourceLocation END = new ResourceLocation(SummonerScrolls.MOD_ID, "chests/end_scrolls");
        public static final ResourceLocation UPGRADE = new ResourceLocation(SummonerScrolls.MOD_ID, "chests/upgrade_scroll");
    }
}