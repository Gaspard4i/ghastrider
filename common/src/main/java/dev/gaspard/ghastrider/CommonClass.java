package dev.gaspard.ghastrider;

import dev.gaspard.ghastrider.platform.Services;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("GhastRider loaded on {}!", Services.PLATFORM.getPlatformName());
    }
}
