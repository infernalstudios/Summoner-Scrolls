package com.github.teamfusion.summonerscrolls;

import com.github.teamfusion.summonerscrolls.client.SummonerClient;
import com.github.teamfusion.summonerscrolls.client.particle.SummonerScrollsParticles;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerCreativeTab;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.github.teamfusion.summonerscrolls.common.util.loot.SummonerLootTables;
import com.github.teamfusion.summonerscrolls.common.util.trade.SummonerTrades;
import com.github.teamfusion.summonerscrolls.platform.Environment;
import com.github.teamfusion.summonerscrolls.platform.ModInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummonerScrolls {
    public static final String MOD_ID = "summonerscrolls";
    public static final String MOD_NAME = "Summoner Scrolls";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID).client(SummonerClient::commonClientInitialize).postClient(SummonerClient::postClientInitialize).common(ModConfig::register).build();


    public static void commonInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        INSTANCE.bootstrap();

        SummonerItems.register();
        SummonerEntityTypes.register();
        SummonerSoundEvents.SOUND_EVENTS.register();
        SummonerCreativeTab.TABS.register();

        SummonerEntityTypes.postRegister();

        SummonerScrollsParticles.init();
        SummonerLootTables.init();
        SummonerTrades.init();
    }
}