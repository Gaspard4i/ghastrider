package dev.gaspard.ghastrider;

public interface GhastStatsAccess {

	float ghastrider$getSpeed();

	float ghastrider$getDashStrength();

	float ghastrider$getCooldownFactor();

	void ghastrider$setStats(float speed, float dashStrength, float cooldown);

	boolean ghastrider$hasStats();
}
