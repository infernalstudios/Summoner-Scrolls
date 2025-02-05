package com.github.teamfusion.summonerscrolls.forge;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.registry.SummonerEvents;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = SummonerScrolls.MOD_ID)
public class CommonEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void anvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        Item leftItem = left.getItem();
        ItemStack right = event.getRight();
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

                    displayTag.put("Lore", lore);
                }
                if (event.getName() != null && !event.getName().isEmpty()) {
                    copy.setHoverName(Component.literal(event.getName()));
                }

                event.setOutput(copy);
                event.setCost(8);
            }
        }
    }

    @SubscribeEvent()
    public static void onEntityInteract(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        SummonerEvents.useScroll(player, hand);
    }
}