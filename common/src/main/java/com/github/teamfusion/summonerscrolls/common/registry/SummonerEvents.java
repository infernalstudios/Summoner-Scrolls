package com.github.teamfusion.summonerscrolls.common.registry;

import com.github.teamfusion.summonerscrolls.common.entity.SummonerEntityTypes;
import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SummonerEvents {

    public static void useScroll(Player player, InteractionHand hand) {
        Level level = player.getLevel();

        if (level.isClientSide()) {
            return;
        }

        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() instanceof TieredItem item) {
            int xpCost = ScrollUtil.getXP(itemStack);
            boolean isCreative = player.isCreative();

            if (!isCreative && player.experienceLevel < xpCost) {
                player.displayClientMessage(Component.translatable("message.summonerscrolls.not_enough_xp", xpCost), true);
                return;
            }

            if (player.getCooldowns().isOnCooldown(item)) {
                float cooldownTicks = player.getCooldowns().getCooldownPercent(item, 0);
                int remainingTicks = (int) (cooldownTicks * 600);
                int remainingSeconds = Math.max(0, remainingTicks / 20);
                player.displayClientMessage(Component.translatable("message.summonerscrolls.cooldown", remainingSeconds), true);
                return;
            }

            if (!isCreative) {
                player.giveExperienceLevels(-xpCost);
            }

            EntityType<?> entityType = ScrollUtil.getEntityType(itemStack);
            ServerLevel serverLevel = (ServerLevel) level;
            BlockPos spawnPos = player.blockPosition().offset(player.getDirection().getNormal());

            Entity entity = entityType.spawn(serverLevel, itemStack, player, spawnPos, MobSpawnType.MOB_SUMMONED, true, true);

            //TODO: MAKE THIS PART OF THE SCROLL PROPERTIES!!! Once they no longer use enchantments for them that is...
            if (entityType == SummonerEntityTypes.BEE_SUMMON.get()) {
                for (int i = 0; i < 4; i++) {
                    entityType.spawn(serverLevel, itemStack, player, spawnPos, MobSpawnType.MOB_SUMMONED, true, true);
                }
            } else if (entityType == SummonerEntityTypes.CAVE_SPIDER_SUMMON.get()) {
                for (int i = 0; i < 2; i++) {
                    entityType.spawn(serverLevel, itemStack, player, spawnPos, MobSpawnType.MOB_SUMMONED, true, true);
                }
            }


            if (entity instanceof ISummon summon) {
                summon.setOwnerUUID(player.getUUID());

                if (!isCreative) {
                    player.getCooldowns().addCooldown(item, 1200);
                    summon.setDespawnDelay(600);
                    itemStack.hurt(ScrollUtil.getDurability(itemStack), level.random, (ServerPlayer) player);
                }

                level.gameEvent(player, GameEvent.ENTITY_PLACE, spawnPos);
                player.displayClientMessage(Component.translatable("message.summonerscrolls.summon_success", entity.getDisplayName().getString()), true);
            }
        }
    }
}