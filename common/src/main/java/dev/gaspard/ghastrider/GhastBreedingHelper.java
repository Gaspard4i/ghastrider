package dev.gaspard.ghastrider;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;

public class GhastBreedingHelper {

	public static void inheritStats(HappyGhast baby, HappyGhast parentA, HappyGhast parentB, RandomSource random) {
		float speedA = GhastStatHelper.getEffectiveSpeed(parentA);
		float speedB = GhastStatHelper.getEffectiveSpeed(parentB);
		float dashA = GhastStatHelper.getEffectiveDashStrength(parentA);
		float dashB = GhastStatHelper.getEffectiveDashStrength(parentB);
		float cooldownA = GhastStatHelper.getEffectiveCooldown(parentA);
		float cooldownB = GhastStatHelper.getEffectiveCooldown(parentB);

		float babySpeed = breedStat(speedA, speedB, random);
		float babyDash = breedStat(dashA, dashB, random);
		float babyCooldown = breedStat(cooldownA, cooldownB, random);

		((GhastStatsAccess) baby).ghastrider$setStats(babySpeed, babyDash, babyCooldown);
	}

	private static float breedStat(float parentA, float parentB, RandomSource random) {
		float randomComponent = Mth.nextFloat(random, Constants.STAT_MIN, Constants.STAT_MAX);
		float result = (parentA + parentB + randomComponent) / 3.0f;
		return Mth.clamp(result, Constants.STAT_MIN, Constants.STAT_MAX);
	}

	public static void randomizeStats(HappyGhast ghast, RandomSource random) {
		float speed = randomStat(random);
		float dash = randomStat(random);
		float cooldown = randomStat(random);
		((GhastStatsAccess) ghast).ghastrider$setStats(speed, dash, cooldown);
	}

	private static float randomStat(RandomSource random) {
		float value = Constants.STAT_SPAWN_MEAN + (float) random.nextGaussian() * Constants.STAT_SPAWN_SIGMA;
		return Mth.clamp(value, Constants.STAT_MIN, Constants.STAT_MAX);
	}
}
