package com.github.teamfusion.summonerscrolls;

import com.github.teamfusion.summonerscrolls.client.SSClient;
import com.github.teamfusion.summonerscrolls.client.particle.SummonerScrollsParticles;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerEnchantments;
import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import com.github.teamfusion.summonerscrolls.common.sound.SummonerSoundEvents;
import com.github.teamfusion.summonerscrolls.common.util.loot.SummonerLootTables;
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

    public static final CreativeModeTab SCROLLS_TAB = Environment.createTab(new ResourceLocation(MOD_ID, "scrolls_tab"), () -> new ItemStack(SummonerItems.ENHANCEMENT_SCROLL.get()));
    
    public static void commonInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        INSTANCE.bootstrap();

        SummonerEnchantments.ENCHANTMENTS.register();
        SummonerItems.ITEMS.register();
        SummonerEntityTypes.ENTITY_TYPES.register();
        SummonerSoundEvents.register();

        SummonerEntityTypes.postRegister();

        SummonerScrollsParticles.init();
        SummonerLootTables.init();
        SSTrades.init();
    }
}