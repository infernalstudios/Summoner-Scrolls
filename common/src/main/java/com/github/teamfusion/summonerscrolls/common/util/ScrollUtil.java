package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ScrollUtil {

    public static EntityType<?> getEntityType(CompoundTag nbt) {
        return BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(nbt.getString("summon")));
    }

    public static int getEntityCount(CompoundTag nbt) {
        return nbt.getInt("count");
    }

    public static int getCost(CompoundTag nbt) {
        return nbt.getInt("cost");
    }

    public static int getDamage(CompoundTag nbt) {
        return nbt.getInt("damageAmount");
    }

    public static boolean hasScrollProperties(CompoundTag nbt) {
        return nbt.contains("summon") && nbt.contains("count") && nbt.contains("cost") && nbt.contains("damageAmount");
    }

    public static boolean onAnvilChange(AnvilMenu container, ItemStack left, ItemStack right, Container outputSlot, String name, int baseCost, Player player) {
        Item leftItem = left.getItem();
        Item rightItem = right.getItem();

        CompoundTag leftNbt = left.getOrCreateTag();

        if (rightItem instanceof ScrollItem && !ScrollUtil.hasScrollProperties(leftNbt)) {

            if (leftItem instanceof TieredItem) {

                ItemStack copy = left.copy();
                CompoundTag scrollTag = right.getTag();

                if (scrollTag != null) {
                    CompoundTag resultTag = copy.getOrCreateTag();
                    resultTag.merge(scrollTag);

                    CompoundTag displayTag = copy.getOrCreateTagElement("display");
                    ListTag lore = new ListTag();

                    if (resultTag.getInt("count") <= 1) {
                        lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(
                                "item.summonerscrolls.summoner_scroll_desc_a", ScrollUtil.getEntityType(resultTag).getDescription()
                        ))));
                    }
                    else {
                        lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(
                                "item.summonerscrolls.summoner_scroll_desc_b", resultTag.getInt("count"),
                                ScrollUtil.getEntityType(resultTag).getDescription()
                        ))));
                    }

                    lore.add(StringTag.valueOf(Component.Serializer.toJson(Component.translatable(
                            "item.summonerscrolls.summoner_scroll_desc_c", resultTag.getInt("cost")
                    ).withStyle(ChatFormatting.GREEN))));

                    displayTag.put("Lore", lore);
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