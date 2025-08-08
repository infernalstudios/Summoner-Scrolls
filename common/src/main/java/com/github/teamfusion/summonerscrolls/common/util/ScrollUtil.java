package com.github.teamfusion.summonerscrolls.common.util;

import com.github.teamfusion.summonerscrolls.common.config.ConfigEntries;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;

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

    public static void removeScrollFromItem(ItemStack stack) {
        Item enhancedItem = stack.getItem();

        if (enhancedItem instanceof TieredItem) {
            stack.removeTagKey("summon");
            stack.removeTagKey("count");
            stack.removeTagKey("cost");
            stack.removeTagKey("damageAmount");

            CompoundTag displayTag = stack.getOrCreateTagElement("display");
            ListTag loreList = displayTag.getList("Lore", 8);

            loreList.remove(loreList.size() - 1);
            loreList.remove(loreList.size() - 1);

            stack.addTagElement("Lore", loreList);
        }
    }

    public static boolean applyScrollToItemInAnvil(AnvilMenu container, ItemStack left, ItemStack right, Container outputSlot, String name) {
        Item leftItem = left.getItem();
        Item rightItem = right.getItem();

        CompoundTag leftNbt = left.getOrCreateTag();

        if (rightItem instanceof ScrollItem && !hasScrollProperties(leftNbt)) {

            if (leftItem.builtInRegistryHolder().is(TagKey.create(Registries.ITEM,
                    new ResourceLocation("summonerscrolls", "summoning_items")))){

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
