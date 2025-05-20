package com.github.teamfusion.summonerscrolls.common.util.cooldowns;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

//TODO: Right now this class is pretty 1-to-1 with the vanilla cooldowns, with some unnecessary things.
// Don't forget to refactor/clean up once the cooldown system is fully implemented and working
public class SummonerItemCooldowns {
    private final Map<ItemStack, SummonerCooldownInstance> cooldowns = Maps.newHashMap();
    private int tickCount;

    public static SummonerItemCooldowns createSummonerItemCooldowns(){
        return new SummonerItemCooldowns();
    }

    public boolean isOnCooldown(ItemStack itemStack) {
        return this.getCooldownPercent(itemStack, 0.0F) > 0.0F;
    }

    public float getCooldownPercent(ItemStack itemStack, float f) {
        SummonerCooldownInstance CooldownInstance = this.cooldowns.get(itemStack);
        if (CooldownInstance != null) {
            float g = (float)(CooldownInstance.endTime - CooldownInstance.startTime);
            float h = (float)CooldownInstance.endTime - ((float)this.tickCount + f);
            return Mth.clamp(h / g, 0.0F, 1.0F);
        } else {

            return 0.0F;
        }
    }

    public void tick() {
        ++this.tickCount;
        if (!this.cooldowns.isEmpty()) {
            Iterator<Map.Entry<ItemStack, SummonerCooldownInstance>> iterator = this.cooldowns.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<ItemStack, SummonerItemCooldowns.SummonerCooldownInstance> entry = (Map.Entry)iterator.next();
                if (((SummonerCooldownInstance)entry.getValue()).endTime <= this.tickCount) {
                    iterator.remove();
                    this.onCooldownEnded((ItemStack)entry.getKey());
                }
            }
        }
    }

    public void addCooldown(ItemStack itemStack, int i) {
        this.cooldowns.put(itemStack, new SummonerItemCooldowns.SummonerCooldownInstance(this.tickCount, this.tickCount + i));
        this.onCooldownStarted(itemStack, i);
    }

    public void removeCooldown(ItemStack itemStack) {
        this.cooldowns.remove(itemStack);
        this.onCooldownEnded(itemStack);
    }

    protected void onCooldownStarted(ItemStack itemStack, int i) {
    }

    protected void onCooldownEnded(ItemStack itemStack) {
    }

    static class SummonerCooldownInstance {
        final int startTime;
        final int endTime;

        SummonerCooldownInstance(int i, int j) {
            this.startTime = i;
            this.endTime = j;
        }
    }
}
