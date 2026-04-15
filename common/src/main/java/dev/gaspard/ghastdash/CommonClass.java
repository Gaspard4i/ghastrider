package dev.gaspard.ghastdash;

import dev.gaspard.ghastdash.platform.Services;

public class CommonClass {

    public static void init() {
        Constants.LOG.info("Happy Ghast Dash loaded on {}!", Services.PLATFORM.getPlatformName());
    }
}
