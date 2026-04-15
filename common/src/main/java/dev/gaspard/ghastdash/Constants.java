package dev.gaspard.ghastdash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "ghastdash";
    public static final String MOD_NAME = "Happy Ghast Dash";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // Dash mechanics
    public static final float MAX_CHARGE_TICKS = 40f; // 2 seconds to full charge
    public static final float MIN_DASH_MULTIPLIER = 1.5f;
    public static final float MAX_DASH_MULTIPLIER = 5.0f;
    public static final int DASH_COOLDOWN_TICKS = 60; // 3 seconds cooldown
    public static final float VERTICAL_BOOST_FACTOR = 0.3f;
}
