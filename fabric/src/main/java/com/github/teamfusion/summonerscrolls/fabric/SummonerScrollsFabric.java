package com.github.teamfusion.summonerscrolls.fabric;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.config.ModConfig;
import com.github.teamfusion.summonerscrolls.common.registry.SSEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;

public class SummonerScrollsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register((player, world, hand)-> {
            SSEvents.useScroll(player, hand);
            return InteractionResultHolder.pass(ItemStack.EMPTY);
        });
        SummonerScrolls.commonInitialize();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(SummonerScrolls.MOD_ID, "reload_listener");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                ModConfig.register();
            }
        });
    }
}