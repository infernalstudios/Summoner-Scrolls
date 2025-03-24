package com.github.teamfusion.summonerscrolls.common.registry;


import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import com.github.teamfusion.summonerscrolls.platform.common.CreativeTabRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class SummonerCreativeTab {

    public static final CoreRegistry<CreativeModeTab> TABS = CoreRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, SummonerScrolls.MOD_ID);

    public static final Supplier<CreativeModeTab> SCROLLS_TAB = TABS.register("summoner_scrolls", () -> createTab(
            "itemGroup.summonerscrolls.scrolls_tab",

            () -> new ItemStack(SummonerItems.ENHANCEMENT_SCROLL.get()),

            (itemDisplayParameters, output) -> {

                // Scrolls

                output.accept(SummonerItems.ZOMBIE_SCROLL.get());
                output.accept(SummonerItems.SPIDER_SCROLL.get());
                output.accept(SummonerItems.SPIDER_JOCKEY_SCROLL.get());
                output.accept(SummonerItems.SKELETON_SCROLL.get());
                output.accept(SummonerItems.BEE_SCROLL.get());

                output.accept(SummonerItems.HUSK_SCROLL.get());
                output.accept(SummonerItems.STRAY_SCROLL.get());
                output.accept(SummonerItems.CAVE_SPIDER_SCROLL.get());
                output.accept(SummonerItems.ENDERMAN_SCROLL.get());
                output.accept(SummonerItems.PIGLIN_SCROLL.get());

                output.accept(SummonerItems.CREEPER_SCROLL.get());
                output.accept(SummonerItems.CHARGED_CREEPER_SCROLL.get());
                output.accept(SummonerItems.PIGLIN_BRUTE_SCROLL.get());
                output.accept(SummonerItems.SHULKERMAN_SCROLL.get());
                output.accept(SummonerItems.IRON_GOLEM_SCROLL.get());

                output.accept(SummonerItems.ENHANCEMENT_SCROLL.get());

                // Tools

                output.accept(SummonerItems.SUMMON_BOW.get());
                output.accept(SummonerItems.SUMMON_CROSSBOW.get());
                output.accept(SummonerItems.SUMMON_ARROW.get());
                output.accept(SummonerItems.SUMMON_SWORD.get());
                output.accept(SummonerItems.SUMMON_AXE.get());
                output.accept(SummonerItems.SUMMON_PICKAXE.get());
                output.accept(SummonerItems.SUMMON_SHOVEL.get());
                output.accept(SummonerItems.SUMMON_HOE.get());
            })
    );

    public static CreativeModeTab createTab(String string, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator itemsGenerator) {
        return new CreativeTabRegistry().title(Component.translatable(string)).icon(icon).displayItems(itemsGenerator).build();
    }
}