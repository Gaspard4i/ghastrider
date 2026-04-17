package dev.gaspard.ghastrider.client;

import dev.gaspard.ghastrider.Constants;
import dev.gaspard.ghastrider.DashState;
import dev.gaspard.ghastrider.GhastStatHelper;
import dev.gaspard.ghastrider.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.phys.Vec3;

public class DashHandler {

	private static final DashState state = new DashState();
	private static boolean wasDashKeyDown = false;
	private static boolean wasOnCooldown = false;
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

		boolean wasCooldown = state.isOnCooldown();
		state.tick();

		// Key just pressed
		if (dashKeyDown && !wasDashKeyDown) {
			state.startCharging();
		}

		// Cooldown just ended while G is held — auto-start charging
		if (dashKeyDown && wasCooldown && !state.isOnCooldown() && !state.isCharging()) {
			state.startCharging();
		}

		// Key just released while charging
		if (!dashKeyDown && wasDashKeyDown && state.isCharging()) {
			float cooldownFactor = GhastStatHelper.getEffectiveCooldown(ghast);
			float power = state.release(cooldownFactor);
			if (power >= 0) {
				applyDash(player, ghast, power);
			}
		}

		wasDashKeyDown = dashKeyDown;
	}

	private static void applyDash(LocalPlayer player, HappyGhast ghast, float power) {
		float speedStat = GhastStatHelper.getEffectiveSpeed(ghast);
		float dashStat = GhastStatHelper.getEffectiveDashStrength(ghast);

		float baseMultiplier = Constants.MIN_DASH_MULTIPLIER
				+ power * (Constants.MAX_DASH_MULTIPLIER - Constants.MIN_DASH_MULTIPLIER);
		float multiplier = baseMultiplier * speedStat * dashStat;

		Vec3 lookDir = player.getLookAngle();
		double dashX = lookDir.x * multiplier;
		double dashY = lookDir.y * multiplier * 0.5 + Constants.VERTICAL_BOOST_FACTOR;
		double dashZ = lookDir.z * multiplier;

		ghast.setDeltaMovement(ghast.getDeltaMovement().add(dashX, dashY, dashZ));
		ghast.hurtMarked = true;

		playDashSound(player, power);

		Constants.LOG.debug("Dash! power={}, multiplier={}, speed={}, dash={}", power, multiplier, speedStat, dashStat);
	}

	private static void playDashSound(LocalPlayer player, float power) {
		Minecraft mc = Minecraft.getInstance();

		if (activeDashSound != null) {
			mc.getSoundManager().stop(activeDashSound);
			activeDashSound = null;
		}

		float volume = 0.10f + power * 0.05f;
		float pitch = 0.7f + power * 0.3f;

		activeDashSound = new SimpleSoundInstance(
				ModSounds.DASH.location(),
				SoundSource.NEUTRAL,
				volume,
				pitch,
				SoundInstance.createUnseededRandom(),
				false,
				0,
				SoundInstance.Attenuation.NONE,
				player.getX(), player.getY(), player.getZ(),
				true
		);
		mc.getSoundManager().play(activeDashSound);
	}

	public static boolean isRidingHappyGhast() {
		Minecraft mc = Minecraft.getInstance();
		return mc.player != null && mc.player.getVehicle() instanceof HappyGhast;
	}
}
