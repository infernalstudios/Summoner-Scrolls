package com.github.teamfusion.summonerscrolls.common.config;

public class ConfigEntries {
    public static void reload() {
        ConfigEntry.Scroll.reload();

        anvilXPCost = new ConfigEntry<>("scrolls.anvil_xp_cost", 8).get();
    }

    public static int anvilXPCost;

    public static ConfigEntry.Scroll zombie = new ConfigEntry.Scroll("scrolls.zombie", 1, 1);
    public static ConfigEntry.Scroll spider = new ConfigEntry.Scroll("scrolls.spider", 1, 1);
    public static ConfigEntry.Scroll spiderJockey = new ConfigEntry.Scroll("scrolls.spider_jockey", 3, 1);
    public static ConfigEntry.Scroll skeleton = new ConfigEntry.Scroll("scrolls.skeleton", 2, 2);
    public static ConfigEntry.Scroll bee = new ConfigEntry.Scroll("scrolls.bee", 3, 5);

    public static ConfigEntry.Scroll husk = new ConfigEntry.Scroll("scrolls.husk", 2, 5);
    public static ConfigEntry.Scroll stray = new ConfigEntry.Scroll("scrolls.stray", 4, 5);
    public static ConfigEntry.Scroll caveSpider = new ConfigEntry.Scroll("scrolls.cave_spider", 3, 5);
    public static ConfigEntry.Scroll enderman = new ConfigEntry.Scroll("scrolls.enderman", 4, 5);
    public static ConfigEntry.Scroll piglin = new ConfigEntry.Scroll("scrolls.piglin", 3, 10);

    public static ConfigEntry.Scroll creeper = new ConfigEntry.Scroll("scrolls.creeper", 4, 10);
    public static ConfigEntry.Scroll piglinBrute = new ConfigEntry.Scroll("scrolls.piglin_brute", 5, 10);
    public static ConfigEntry.Scroll shulkerman = new ConfigEntry.Scroll("scrolls.shulkerman", 5, 10);
    public static ConfigEntry.Scroll ironGolem = new ConfigEntry.Scroll("scrolls.iron_golem", 5, 10);
    public static ConfigEntry.Scroll chargedCreeper = new ConfigEntry.Scroll("scrolls.charged_creeper", 5, 15);
}
