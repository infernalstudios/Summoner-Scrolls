package com.github.teamfusion.summonerscrolls.common.registry;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.common.enchantment.ScrollEnchantment;
import com.github.teamfusion.summonerscrolls.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public class SummonerEnchantments {
    public static final CoreRegistry<Enchantment> ENCHANTMENTS = CoreRegistry.create(Registry.ENCHANTMENT, SummonerScrolls.MOD_ID);

    /* Summon Enchantments - Tier 1 */
    public static final Supplier<Enchantment> ZOMBIE_SCROLL_ENCHANTMENT = register("zombie_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> SPIDER_SCROLL_ENCHANTMENT = register("spider_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> SPIDER_JOCKEY_SCROLL_ENCHANTMENT = register("spider_jockey_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> SKELETON_SCROLL_ENCHANTMENT = register("skeleton_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> BEE_SCROLL_ENCHANTMENT = register("bee_scroll", new ScrollEnchantment());

    /* Summon Enchantments - Tier 2 */
    public static final Supplier<Enchantment> HUSK_SCROLL_ENCHANTMENT = register("husk_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> STRAY_SCROLL_ENCHANTMENT = register("stray_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> CAVE_SPIDER_SCROLL_ENCHANTMENT = register("cave_spider_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> ENDERMAN_SCROLL_ENCHANTMENT = register("enderman_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> PIGLIN_SCROLL_ENCHANTMENT = register("piglin_scroll", new ScrollEnchantment());

    /* Summon Entities - Tier 3 */
    public static final Supplier<Enchantment> CREEPER_SCROLL_ENCHANTMENT = register("creeper_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> CHARGED_CREEPER_SCROLL_ENCHANTMENT = register("charged_creeper_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> PIGLIN_BRUTE_SCROLL_ENCHANTMENT = register("piglin_brute_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> SHULKERMAN_SCROLL_ENCHANTMENT = register("shulkerman_scroll", new ScrollEnchantment());
    public static final Supplier<Enchantment> IRON_GOLEM_SCROLL_ENCHANTMENT = register("iron_golem_scroll", new ScrollEnchantment());

    private static Supplier<Enchantment> register(String id, Enchantment enchantment) {
        return ENCHANTMENTS.register(id, () -> enchantment);
    }
}