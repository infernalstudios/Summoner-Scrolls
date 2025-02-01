package com.github.teamfusion.summonerscrolls.datagen.client;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public final class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        gen.generateFlatItem(SummonerItems.ZOMBIE_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.SPIDER_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.SPIDER_JOCKEY_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.SKELETON_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.BEE_SCROLL.get(), ModelTemplates.FLAT_ITEM);

        gen.generateFlatItem(SummonerItems.HUSK_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.STRAY_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.CAVE_SPIDER_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.ENDERMAN_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.PIGLIN_SCROLL.get(), ModelTemplates.FLAT_ITEM);

        gen.generateFlatItem(SummonerItems.CREEPER_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.CHARGED_CREEPER_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.PIGLIN_BRUTE_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.SHULKERMAN_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.IRON_GOLEM_SCROLL.get(), ModelTemplates.FLAT_ITEM);

        gen.generateFlatItem(SummonerItems.ENHANCEMENT_SCROLL.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.INVISIBLE_SUMMON_LIGHT.get(), ModelTemplates.FLAT_ITEM);

        gen.generateFlatItem(SummonerItems.SUMMON_ARROW.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(SummonerItems.SUMMON_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(SummonerItems.SUMMON_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(SummonerItems.SUMMON_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(SummonerItems.SUMMON_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        gen.generateFlatItem(SummonerItems.SUMMON_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    //todo: bow model
}