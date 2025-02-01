package com.github.teamfusion.summonerscrolls.datagen;

import com.github.teamfusion.summonerscrolls.datagen.client.ModelGenerator;
import com.github.teamfusion.summonerscrolls.datagen.common.loot.EntityLootGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class SummonerScrollsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModelGenerator::new);
        gen.addProvider(EntityLootGenerator::new);
    }
    //todo: particle and sounds generator
}