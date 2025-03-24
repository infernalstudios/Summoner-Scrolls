package com.github.teamfusion.summonerscrolls.common.item;

import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ScrollItem<T extends Entity> extends Item {

    private final Supplier<EntityType<T>> entitySupplier;
    private final int count;
    private final int cost;
    private final int damageAmount;

    public ScrollItem(Supplier<EntityType<T>> entitySupplier, int count, Properties properties, int cost, int damageAmount) {
        super(properties);
        this.entitySupplier = entitySupplier;
        this.count = count;
        this.cost = cost;
        this.damageAmount = damageAmount;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, list, tooltipFlag);
        list.add((Component.translatable("item.summonerscrolls.scroll.xp_warning")).append(String.valueOf(this.cost)).withStyle(ChatFormatting.AQUA));

        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("summon", BuiltInRegistries.ENTITY_TYPE.getKey(this.entitySupplier.get()).toString());
        nbt.putInt("count", this.count);
        nbt.putInt("cost", this.cost);
        nbt.putInt("damageAmount", this.damageAmount);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}