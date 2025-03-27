package com.github.teamfusion.summonerscrolls.fabric;

import com.github.teamfusion.summonerscrolls.client.SummonerClient;
import net.fabricmc.api.ClientModInitializer;

public class SummonerScrollsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SummonerClient.commonClientInitialize();
        SummonerClient.postClientInitialize();
    }
}
