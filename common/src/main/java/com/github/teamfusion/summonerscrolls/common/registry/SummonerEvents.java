package com.github.teamfusion.summonerscrolls.common.registry;

import com.github.teamfusion.summonerscrolls.common.entity.base.ISummon;
import com.github.teamfusion.summonerscrolls.common.item.ScrollItem;
import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import com.github.teamfusion.summonerscrolls.common.util.methodHolders.SummonerPlayerMixinHolder;
import com.github.teamfusion.summonerscrolls.common.util.cooldowns.SummonerItemCooldowns;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class SummonerEvents {

    public static void useScroll(Player player, InteractionHand hand) {
        Level level = player.level();
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!itemStack.hasTag()) {return;}

        CompoundTag nbt = itemStack.getOrCreateTag();

        if (!level.isClientSide() && ScrollUtil.hasScrollProperties(nbt)) {

            boolean isCreative = player.isCreative();

            int xpCost = ScrollUtil.getCost(nbt);
            int entityCount = ScrollUtil.getEntityCount(nbt);
            int damage = ScrollUtil.getDamage(nbt);

            SummonerItemCooldowns summonerCooldowns = ((SummonerPlayerMixinHolder)player).summonerscrolls$getSummonerCooldowns();

            if (summonerCooldowns.isOnCooldown(itemStack)) {
                float cooldownTicks = summonerCooldowns.getCooldownPercent(itemStack, 0);
                int remainingTicks = (int) (cooldownTicks * 600);
                int remainingSeconds = Math.max(1, remainingTicks / 20);
                player.displayClientMessage(Component.translatable("message.summonerscrolls.cooldown", remainingSeconds), true);
                return;
            }

            if (!isCreative) {
                if (player.experienceLevel < xpCost) {
                    player.displayClientMessage(Component.translatable("message.summonerscrolls.not_enough_xp", xpCost), true);
                    return;
                }

                player.giveExperienceLevels(-xpCost);
                summonerCooldowns.addCooldown(itemStack, 1200);
                if (item instanceof ScrollItem) {
                    itemStack.hurt(1, level.random, (ServerPlayer) player);
                }
                else {
                    itemStack.hurt(damage, level.random, (ServerPlayer) player);
                }
            }

            EntityType<?> entityType = ScrollUtil.getEntityType(nbt);
            ServerLevel serverLevel = (ServerLevel) level;
            BlockPos spawnPos = player.blockPosition().offset(player.getDirection().getNormal());

            for (int i = 0; i < entityCount; i++) {
                Entity entity = entityType.spawn(serverLevel, itemStack, player, spawnPos, MobSpawnType.MOB_SUMMONED, true, true);

                if (entity instanceof ISummon summon) {
                    summon.setOwnerUUID(player.getUUID());
                    summon.setDespawnDelay(600 + level.getRandom().nextInt(10));

                    player.displayClientMessage(Component.translatable("message.summonerscrolls.summon_success", entity.getDisplayName().getString()), true);

                    int count = 80;

                    for (int j = 0; j < count; j++) {
                        Level world = entity.level();
                        RandomSource random = world.getRandom();

                        float horizontal = random.nextInt(-10, 10) * 0.125f;
                        float vertical = random.nextInt(-8, 8) * 0.125f;

                        ((ServerLevel) world).sendParticles(ParticleTypes.POOF,
                                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(), 1,
                                horizontal, vertical, horizontal, 0);
                    }
                }
            }
            player.playNotifySound(SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 1, 1);

            level.gameEvent(player, GameEvent.ENTITY_PLACE, spawnPos);
        }
    }
}