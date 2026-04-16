package dev.gaspard.ghastrider.client;

import dev.gaspard.ghastrider.Constants;
import dev.gaspard.ghastrider.DashState;
import dev.gaspard.ghastrider.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.phys.Vec3;

/**
 * Client-side handler for the dash mechanic.
 * Called every client tick to manage charge state and apply the dash boost.
 */
public class DashHandler {

    private static final DashState state = new DashState();
    private static boolean wasDashKeyDown = false;
    private static SoundInstance activeDashSound = null;

    public static DashState getState() {
        return state;
    }

    public static void onClientTick(boolean dashKeyDown) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null || !(player.getVehicle() instanceof HappyGhast ghast)) {
            if (state.isCharging()) {
                state.reset();
            }
            wasDashKeyDown = false;
            return;
        }

        state.tick();

        // Key just pressed
        if (dashKeyDown && !wasDashKeyDown) {
            state.startCharging();
        }

        // Key just released while charging
        if (!dashKeyDown && wasDashKeyDown && state.isCharging()) {
            float power = state.release();
            if (power >= 0) {
                applyDash(player, ghast, power);
            }
        }

        wasDashKeyDown = dashKeyDown;
    }

    private static void applyDash(LocalPlayer player, HappyGhast ghast, float power) {
        float multiplier = Constants.MIN_DASH_MULTIPLIER
                + power * (Constants.MAX_DASH_MULTIPLIER - Constants.MIN_DASH_MULTIPLIER);

        Vec3 lookDir = player.getLookAngle();
        double dashX = lookDir.x * multiplier;
        double dashY = lookDir.y * multiplier * 0.5 + Constants.VERTICAL_BOOST_FACTOR;
        double dashZ = lookDir.z * multiplier;

        ghast.setDeltaMovement(ghast.getDeltaMovement().add(dashX, dashY, dashZ));
        ghast.hurtMarked = true; // Force client->server sync

        playDashSound(player, power);

        Constants.LOG.debug("Dash! power={}, multiplier={}", power, multiplier);
    }

    private static void playDashSound(LocalPlayer player, float power) {
        Minecraft mc = Minecraft.getInstance();

        // Stop previous dash sound to prevent overlap when spamming
        if (activeDashSound != null) {
            mc.getSoundManager().stop(activeDashSound);
            activeDashSound = null;
        }

        // Wind burst — short propulsion sound, volume and pitch scale with power
        float volume = 0.6f + power * 0.4f;  // 0.6 to 1.0
        float pitch = 0.8f + power * 0.4f;   // 0.8 to 1.2

        activeDashSound = new SimpleSoundInstance(
                ModSounds.DASH.location(),
                SoundSource.PLAYERS,
                volume,
                pitch,
                SoundInstance.createUnseededRandom(),
                false,  // not looping
                0,      // no delay
                SoundInstance.Attenuation.NONE,
                player.getX(), player.getY(), player.getZ(),
                true    // relative to listener
        );
        mc.getSoundManager().play(activeDashSound);
    }

    public static boolean isRidingHappyGhast() {
        Minecraft mc = Minecraft.getInstance();
        return mc.player != null && mc.player.getVehicle() instanceof HappyGhast;
    }
}
