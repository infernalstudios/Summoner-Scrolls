package com.github.teamfusion.summonerscrolls.common.config;

import com.github.teamfusion.summonerscrolls.SummonerScrolls;
import com.github.teamfusion.summonerscrolls.platform.Environment;
import com.github.teamfusion.summonerscrolls.platform.ModInstance;
import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ModConfig {
    public static final ConfigEntry<Double> VERSION = new ConfigEntry<>("TECHNICAL.VERSION_DO_NOT_EDIT", 0d);
    public static final ConfigEntry<Boolean> RELOAD = new ConfigEntry<>("TECHNICAL.FORCE_RESET", false);

    public static Map CONFIG = new TreeMap<>();


    public static <T> T get(String path, T fallback) {
        return new ConfigEntry<>(path, fallback).get();
    }

    public static void register() {
        register(false);
    }

    public static void register(boolean force) {
        String path = Environment.getConfigDir().resolve(SummonerScrolls.MOD_ID + ".json").toString();

        SummonerScrolls.LOGGER.info("Loading Configs for SummonerScrolls");

        // Create config file if it doesn't exist already
        File config = new File(path);
        boolean create = !config.isFile();

        if (create || force) {
            try {
                config.delete();
                config.createNewFile();

                FileWriter writer = new FileWriter(path);
                writer.write(DEFAULT_CONFIG);
                writer.close();

                SummonerScrolls.LOGGER.info("SummonerScrolls Config file created");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }


        String configContent = DEFAULT_CONFIG;
        try {
            configContent = FileUtils.readFileToString(config, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        CONFIG = new Gson().fromJson(parseJson(configContent), Map.class);
        ConfigEntries.reload();

        if (!force && RELOAD.get()) {
            register(true);
        }
    }

    public static String parseJson(String text) {
        StringBuilder result = new StringBuilder();

        for (String line : text.split("\n")) {
            if (!line.strip().startsWith("//"))
                result.append("\n").append(line);
        }

        return result.toString();
    }

    public static double getVersion() {
        String text = DEFAULT_CONFIG;
        int start = 0;

        while (!List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.').contains(text.charAt(start))) {
            start++;
        }
        int end = start + 1;
        while (List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.').contains(text.charAt(end))) {
            end++;
        }

        return Double.parseDouble(text.substring(start, end));
    }


    public static final String DEFAULT_CONFIG = """
            {
              "TECHNICAL": {
                "VERSION_DO_NOT_EDIT": 1,
                "FORCE_RESET": false
              },
              
              // This config file uses a custom defined parser. That's why there are comments here, they wouldn't be valid in any other .json file.
              //    To add a comment yourself, just start a line with // like I did here
              //    (although their main use is explaining you what the entries do)
              
              // CATEGORY: SCROLLS
              "scrolls": {
                // Level cost to add a scroll to an item in an anvil
                "anvil_xp_cost": 8,
              
                // Summon costs
                "zombie": {
                  "xp_cost": 10,
                  "durability_cost": 1
                },
                "spider": {
                  "xp_cost": 10,
                  "durability_cost": 1
                },
                "spider_jockey": {
                  "xp_cost": 10,
                  "durability_cost": 1
                },
                "skeleton": {
                  "xp_cost": 15,
                  "durability_cost": 2
                },
                "bee": {
                  "xp_cost": 15,
                  "durability_cost": 5
                },
                
                "husk": {
                  "xp_cost": 20,
                  "durability_cost": 5
                },
                "stray": {
                  "xp_cost": 20,
                  "durability_cost": 5
                },
                "cave_spider": {
                  "xp_cost": 15,
                  "durability_cost": 5
                },
                "enderman": {
                  "xp_cost": 30,
                  "durability_cost": 5
                },
                "piglin": {
                  "xp_cost": 30,
                  "durability_cost": 10
                },
                
                "creeper": {
                  "xp_cost": 40,
                  "durability_cost": 10
                },
                "piglin_brute": {
                  "xp_cost": 50,
                  "durability_cost": 10
                },
                "shulkerman": {
                  "xp_cost": 40,
                  "durability_cost": 10
                },
                "iron_golem": {
                  "xp_cost": 50,
                  "durability_cost": 10
                },
                "charged_creeper": {
                  "xp_cost": 60,
                  "durability_cost": 15
                }
              }
            }""";
}
