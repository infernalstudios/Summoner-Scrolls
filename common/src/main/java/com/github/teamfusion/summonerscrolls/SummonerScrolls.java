package com.github.teamfusion.summonerscrolls;

import com.github.teamfusion.summonerscrolls.client.SSClient;
import com.github.teamfusion.summonerscrolls.client.particle.SummonerScrollsParticles;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.registry.SSEnchantments;
import com.github.teamfusion.summonerscrolls.common.registry.SSEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SSItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerScrollsSoundEvents;
import com.github.teamfusion.summonerscrolls.common.util.loot.SSLootTables;
import com.github.teamfusion.summonerscrolls.common.util.trade.SSTrades;
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
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID).client(SSClient::commonClientInitialize).postClient(SSClient::postClientInitialize).common(ModConfig::register).build();

    public static final CreativeModeTab SCROLLS_TAB = Environment.createTab(new ResourceLocation(MOD_ID, "scrolls_tab"), () -> new ItemStack(SSItems.ENHANCEMENT_SCROLL.get()));
    
    public static void commonInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        INSTANCE.bootstrap();

        SSEnchantments.ENCHANTMENTS.register();
        SSItems.ITEMS.register();
        SSEntityTypes.ENTITY_TYPES.register();
        SummonerScrollsSoundEvents.SOUND_EVENTS.register();

        SSEntityTypes.postRegister();

        SummonerScrollsParticles.init();
        SSLootTables.init();
        SSTrades.init();

//        EnvExecutor.runInEnv(EnvType.CLIENT, () -> SSClient::commonClientInitialize);
    }

    public static void log(String message) {
        LOGGER.info(message);
    }

    //todo: put todos in respective classes
    //todo: blue enchantment glare for summon scrolls
    //todo: create spawn eggs
    //TODO: add enchantment target for all tools
    //todo: add comments for everything
    //todo: use less accesswidener stuff and more accessors
}