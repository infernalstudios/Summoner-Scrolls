package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused"})
public class InventoryUtil {
    static List<Enchantment> enchantmentsToRemove = Arrays.asList(
            SummonerEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.SPIDER_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.SKELETON_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.BEE_SCROLL_ENCHANTMENT.get(),

            SummonerEnchantments.HUSK_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.STRAY_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get(),

            SummonerEnchantments.CREEPER_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get(),
            SummonerEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()
    );

    public static boolean onAnvilChange(AnvilMenu container, ItemStack left, ItemStack right, Container outputSlot, String name, int baseCost, Player player) {
        Item leftItem = left.getItem();
        Item rightItem = right.getItem();

        if (rightItem instanceof ScrollItem scrollItem) {
//            Enchantment enchantment = scrollItem.getEnchantment().get();

            if ((leftItem instanceof DiggerItem || leftItem instanceof SwordItem)) {
                ItemStack copy = left.copy();
                CompoundTag scrollTag = right.getTag();

                if (scrollTag != null) {
                    CompoundTag resultTag = copy.getOrCreateTag();
                    resultTag.merge(scrollTag);
                }
                if (name != null && !name.isEmpty()) {
                    copy.setHoverName(Component.literal(name));
                }

                outputSlot.setItem(0, copy);
                container.cost.set(ConfigEntries.anvilXPCost);
                return false;
            }
            return false;
        }
        return true;
    }
}