package dev.gaspard.ghastrider;

/**
 * Tracks the dash charge/cooldown state for a player riding a Happy Ghast.
 * One instance per player on the client side.
 */
public class DashState {

    private boolean charging;
    private float chargeTicks;
    private int cooldownTicks;
    private int cooldownMax;
    private boolean decaying;

    public void startCharging() {
        if (cooldownTicks <= 0 && !charging) {
            charging = true;
            chargeTicks = 0;
        }
    }

    public void tick() {
        if (charging) {
            if (!decaying) {
                chargeTicks = Math.min(chargeTicks + 1, Constants.MAX_CHARGE_TICKS);
                if (chargeTicks >= Constants.MAX_CHARGE_TICKS) {
                    decaying = true;
                }
            } else {
                float minTicks = Constants.MAX_CHARGE_TICKS * Constants.OVERCHARGE_MIN;
                chargeTicks = Math.max(chargeTicks - Constants.OVERCHARGE_DECAY_RATE, minTicks);
            }
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
        decaying = false;
        float power = chargeTicks / Constants.MAX_CHARGE_TICKS;
        chargeTicks = 0;
        if (power < Constants.MIN_CHARGE_THRESHOLD) {
            // Not enough charge — cancel without dashing or cooldown
            return -1f;
        }
        cooldownMax = (int) (Constants.DASH_COOLDOWN_TICKS * power);
        cooldownTicks = cooldownMax;
        return power;
    }

    public void reset() {
        charging = false;
        decaying = false;
        chargeTicks = 0;
        cooldownTicks = 0;
        cooldownMax = 0;
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
        if (cooldownMax <= 0) return 0f;
        return (float) cooldownTicks / cooldownMax;
    }

    public float getCooldownMaxRatio() {
        return (float) cooldownMax / Constants.DASH_COOLDOWN_TICKS;
    }
}
