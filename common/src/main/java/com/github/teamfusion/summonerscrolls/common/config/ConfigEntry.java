package com.github.teamfusion.summonerscrolls.common.config;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConfigEntry<T> {
    public static class Scroll {
        private static List<Scroll> INSTANCES = new ArrayList<>();

        public String path;
        private final ConfigEntry<Integer> xp_entry;
        private final ConfigEntry<Integer> durability_entry;

        public int xp;
        public int durability;

        public Scroll(String path, int xp, int durability) {
            this.path = path;
            this.xp_entry = new ConfigEntry<>(path + ".xp_cost", xp);
            this.durability_entry = new ConfigEntry<>(path + ".durability_cost", durability);

            this.get();
            INSTANCES.add(this);
        }

        public void get() {
            this.xp = this.xp_entry.get();
            this.durability = this.durability_entry.get();
        }

        public static void reload() {
            for (Scroll scroll : INSTANCES)
                scroll.get();
        }
    }


    private final List<String> path;
    private final T fallback;

    public ConfigEntry(String path) {
        this(path, null);
    }

    public ConfigEntry(String path, @Nullable T fallback) {
        this.path = List.of(path.split("\\."));
        this.fallback = fallback;
    }

    public T get() {
        return this.get(this.fallback);
    }

    @SuppressWarnings("unchecked")
    public T get(T fallback) {
        Map next = ModConfig.CONFIG;
        Object result = null;

        for (String step : this.path) {
            try {
                next = (Map) next.get(step);
            }
            catch (Exception e) {
                if (Objects.equals(step, this.path.get(this.path.size() - 1)))
                    result = next.get(step);
                else {
                    SummonerScrolls.log("Couldn't find config value for path : \"" + this.path + "\", defaulting to " + fallback);
                    return fallback;
                }
            }
            if (next == null) {
                SummonerScrolls.log("Couldn't find config value for path : \"" + this.path + "\", defaulting to " + fallback);
                return fallback;
            }
        }


        if (fallback instanceof Integer)
            return (T) (Integer) Long.valueOf(Math.round(Double.parseDouble(String.valueOf(result)))).intValue();
        if (fallback instanceof Double)
            return (T) Double.valueOf(String.valueOf(result));
        if (fallback instanceof String)
            return (T) String.valueOf(result);
        if (fallback instanceof Boolean)
            return (T) Boolean.valueOf(String.valueOf(result));
        if (fallback instanceof Map)
            return (T) next;
        if (fallback instanceof List)
            return (T) result;

        SummonerScrolls.log("Couldn't find config value for path : \"" + this.path + "\", defaulting to " + fallback);
        return fallback;
    }
}