package com.github.teamfusion.summonerscrolls.common.item;

import com.github.teamfusion.summonerscrolls.common.registry.SummonerItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

public class SummonerBowItem extends BowItem {

    public SummonerBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - timeCharged;
            float f = getPowerForTime(i);
            if (!((double) f < 0.1)) {
                if (!level.isClientSide) {
                    ItemStack arrowStack = new ItemStack(SummonerItems.SUMMON_ARROW.get());
                    ArrowItem arrowItem = (ArrowItem) arrowStack.getItem();
                    AbstractArrow abstractArrow = arrowItem.createArrow(level, arrowStack, player);
                    abstractArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                    abstractArrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

                    if (f == 1.0F) {
                        abstractArrow.setCritArrow(true);
                    }

                    int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                    if (j > 0) {
                        abstractArrow.setBaseDamage(abstractArrow.getBaseDamage() + (double) j * 0.5 + 0.5);
                    }

                    int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                    if (k > 0) {
                        abstractArrow.setKnockback(k);
                    }

                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                        abstractArrow.setSecondsOnFire(100);
                    }

                    level.addFreshEntity(abstractArrow);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(SummonerItems.SUMMON_ARROW.get());
    }
}
