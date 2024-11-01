package com.github.teamfusion.summonerscrolls.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigEntries {
    public static void reload() {
        enchantLimiterDefault = new ConfigEntry<>("enchantments.enchant_limiter.default", 3).get();

    }

    public static int enchantLimiterDefault;
}
