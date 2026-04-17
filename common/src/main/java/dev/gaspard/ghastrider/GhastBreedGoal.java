package dev.gaspard.ghastrider;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.phys.Vec3;

public class GhastBreedGoal extends Goal {

	private static final TargetingConditions PARTNER_TARGETING =
			TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
	private static final double BREED_DISTANCE_SQ = 9.0D;
	private static final int BREED_TIME = 60;

	private final HappyGhast ghast;
	private HappyGhast partner;
	private int loveTime;

	public GhastBreedGoal(HappyGhast ghast) {
		this.ghast = ghast;
		this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		if (!ghast.isInLove()) return false;
		if (!(ghast.level() instanceof ServerLevel)) return false;
		this.partner = findPartner();
		return this.partner != null;
	}

	@Override
	public boolean canContinueToUse() {
		return partner.isAlive() && partner.isInLove() && loveTime < BREED_TIME && !ghast.isPanicking();
	}

	@Override
	public void stop() {
		this.partner = null;
		this.loveTime = 0;
	}

	@Override
	public void tick() {
		ghast.getLookControl().setLookAt(partner, 10.0F, ghast.getMaxHeadXRot());

		// Fly toward partner
		Vec3 toPartner = partner.position().subtract(ghast.position());
		double dist = toPartner.length();
		if (dist > 2.0) {
			Vec3 direction = toPartner.normalize().scale(0.3);
			ghast.setDeltaMovement(ghast.getDeltaMovement().add(direction));
		}

		loveTime++;
		if (loveTime >= adjustedTickDelay(BREED_TIME) && ghast.distanceToSqr(partner) < BREED_DISTANCE_SQ) {
			breed();
		}
	}

	private void breed() {
		ghast.spawnChildFromBreeding((ServerLevel) ghast.level(), partner);
	}

	private HappyGhast findPartner() {
		ServerLevel level = (ServerLevel) ghast.level();
		List<? extends HappyGhast> nearby = level.getNearbyEntities(
				HappyGhast.class,
				PARTNER_TARGETING,
				ghast,
				ghast.getBoundingBox().inflate(16.0D)
		);
		double closest = Double.MAX_VALUE;
		HappyGhast best = null;
		for (HappyGhast candidate : nearby) {
			if (candidate != ghast && candidate.isInLove() && ghast.canMate(candidate)) {
				double d = ghast.distanceToSqr(candidate);
				if (d < closest) {
					closest = d;
					best = candidate;
				}
			}
		}
		return best;
	}
}
