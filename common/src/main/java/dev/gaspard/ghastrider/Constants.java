package dev.gaspard.ghastrider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "ghastrider";
    public static final String MOD_NAME = "GhastRider";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // Dash mechanics
    public static final float MAX_CHARGE_TICKS = 40f; // 2 seconds to full charge
    public static final float MIN_DASH_MULTIPLIER = 1.5f;
    public static final float MAX_DASH_MULTIPLIER = 5.0f;
    public static final int DASH_COOLDOWN_TICKS = 60; // 3 seconds cooldown
    public static final float VERTICAL_BOOST_FACTOR = 0.3f;
    public static final float OVERCHARGE_DECAY_RATE = 0.25f; // Ticks of charge lost per tick when held too long
    public static final float MIN_CHARGE_THRESHOLD = 1f / 9f; // Must charge at least 2 bars (2/18) to dash
    public static final float OVERCHARGE_MIN = MIN_CHARGE_THRESHOLD; // Decay stops at the same minimum

    // Ghast individual stats
    public static final float STAT_MIN = 0.5f;
    public static final float STAT_MAX = 1.5f;
    public static final float STAT_DEFAULT = 1.0f; // Neutral baseline for multiplier calculations
    public static final float STAT_SPAWN_MEAN = 0.65f; // Spawn mean: most ghasts are slow
    public static final float STAT_SPAWN_SIGMA = 0.1f; // Tight distribution: ~75% land between 0.55-0.75
    public static final float SPEED_BOOST_PER_LEVEL = 0.15f;
}
