package com.github.teamfusion.summonerscrolls.common.config;

public class ConfigEntries {
    public static void reload() {
        ConfigEntry.Scroll.reload();
    }

    public static ConfigEntry.Scroll zombie = new ConfigEntry.Scroll("scrolls.zombie", 10, 1);
    public static ConfigEntry.Scroll spider = new ConfigEntry.Scroll("scrolls.spider", 10, 1);
    public static ConfigEntry.Scroll spiderJockey = new ConfigEntry.Scroll("scrolls.spider_jockey", 10, 1);
    public static ConfigEntry.Scroll skeleton = new ConfigEntry.Scroll("scrolls.skeleton", 15, 2);
    public static ConfigEntry.Scroll bee = new ConfigEntry.Scroll("scrolls.bee", 15, 5);

    public static ConfigEntry.Scroll husk = new ConfigEntry.Scroll("scrolls.husk", 20, 5);
    public static ConfigEntry.Scroll stray = new ConfigEntry.Scroll("scrolls.stray", 20, 5);
    public static ConfigEntry.Scroll caveSpider = new ConfigEntry.Scroll("scrolls.cave_spider", 15, 5);
    public static ConfigEntry.Scroll enderman = new ConfigEntry.Scroll("scrolls.enderman", 30, 5);
    public static ConfigEntry.Scroll piglin = new ConfigEntry.Scroll("scrolls.piglin", 30, 10);

    public static ConfigEntry.Scroll creeper = new ConfigEntry.Scroll("scrolls.creeper", 40, 10);
    public static ConfigEntry.Scroll piglinBrute = new ConfigEntry.Scroll("scrolls.piglin_brute", 50, 10);
    public static ConfigEntry.Scroll shulkerman = new ConfigEntry.Scroll("scrolls.shulkerman", 40, 10);
    public static ConfigEntry.Scroll ironGolem = new ConfigEntry.Scroll("scrolls.iron_golem", 50, 10);
    public static ConfigEntry.Scroll chargedCreeper = new ConfigEntry.Scroll("scrolls.charged_creeper", 60, 15);

}
