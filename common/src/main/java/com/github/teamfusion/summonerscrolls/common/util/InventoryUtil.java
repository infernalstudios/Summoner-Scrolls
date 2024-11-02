package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.registry.SSEnchantments;
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
            SSEnchantments.ZOMBIE_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.SPIDER_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.SPIDER_JOCKEY_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.SKELETON_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.BEE_SCROLL_ENCHANTMENT.get(),

            SSEnchantments.HUSK_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.STRAY_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.CAVE_SPIDER_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.ENDERMAN_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.PIGLIN_SCROLL_ENCHANTMENT.get(),

            SSEnchantments.CREEPER_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.CHARGED_CREEPER_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.PIGLIN_BRUTE_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.SHULKERMAN_SCROLL_ENCHANTMENT.get(),
            SSEnchantments.IRON_GOLEM_SCROLL_ENCHANTMENT.get()
    );

    public static boolean onAnvilChange(AnvilMenu container, ItemStack left, ItemStack right, Container outputSlot, String name, int baseCost, Player player) {
        Item leftItem = left.getItem();
        Item rightItem = right.getItem();

        if (rightItem instanceof ScrollItem scrollItem) {
            Enchantment enchantment = scrollItem.getEnchantment().get();

            if ((leftItem instanceof DiggerItem || leftItem instanceof SwordItem)) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
                for (Enchantment enchantment2 : enchantmentsToRemove) {
                    if (enchantments.containsKey(enchantment2)) {
                        enchantments.remove(enchantment2);
                    }
                }

                boolean canEnchant = true;

                if (canEnchant) {
                    enchantments.put(enchantment, 1);
                    ItemStack copy = left.copy();
                    EnchantmentHelper.setEnchantments(enchantments, copy);
                    if (name != null && !name.isEmpty()) {
                        copy.setHoverName(Component.literal(name));
                    }

                    outputSlot.setItem(0, copy);
                    container.cost.set(ConfigEntries.anvilXPCost);
                }
            }
            return false;
        }
        return true;
    }
}