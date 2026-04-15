package dev.gaspard.ghastdash;

/**
 * Tracks the dash charge/cooldown state for a player riding a Happy Ghast.
 * One instance per player on the client side.
 */
public class DashState {

    private boolean charging;
    private float chargeTicks;
    private int cooldownTicks;

    public void startCharging() {
        if (cooldownTicks <= 0 && !charging) {
            charging = true;
            chargeTicks = 0;
        }
    }

    public void tick() {
        if (charging) {
            chargeTicks = Math.min(chargeTicks + 1, Constants.MAX_CHARGE_TICKS);
        }
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
    }

    /**
     * Releases the charge and returns the dash power (0.0 to 1.0).
     * Returns -1 if not charging.
     */
    public float release() {
        if (!charging) return -1f;
        charging = false;
        float power = chargeTicks / Constants.MAX_CHARGE_TICKS;
        chargeTicks = 0;
        cooldownTicks = Constants.DASH_COOLDOWN_TICKS;
        return power;
    }

    public void reset() {
        charging = false;
        chargeTicks = 0;
        cooldownTicks = 0;
    }

    public boolean isCharging() {
        return charging;
    }

    public float getChargeProgress() {
        return chargeTicks / Constants.MAX_CHARGE_TICKS;
    }

    public boolean isOnCooldown() {
        return cooldownTicks > 0;
    }

    public float getCooldownProgress() {
        return (float) cooldownTicks / Constants.DASH_COOLDOWN_TICKS;
    }
}
