package com.github.teamfusion.summonerscrolls.common.item;

import com.github.teamfusion.summonerscrolls.common.util.ScrollUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

public class ScrollItem extends Item {

    public ScrollItem(Supplier<Enchantment> enchantment, Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, list, tooltipFlag);
        list.add((Component.translatable("item.summonerscrolls.scroll.xp_warning")).append(String.valueOf(ScrollUtil.getScrollXPCount(stack))).withStyle(ChatFormatting.AQUA));
    }
}