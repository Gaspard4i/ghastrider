package dev.gaspard.ghastrider;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;

public class GhastStatHelper {

	private static final Identifier SPEED_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "speed_stat");
	private static final Identifier DASH_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "dash_strength_stat");
	private static final Identifier COOLDOWN_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "cooldown_factor_stat");

	public static float getEffectiveSpeed(HappyGhast ghast) {
		return applySpeedBoost(ghast, getStatFromModifier(ghast, Attributes.FLYING_SPEED, SPEED_ID));
	}

	public static float getEffectiveDashStrength(HappyGhast ghast) {
		return applySpeedBoost(ghast, getStatFromModifier(ghast, Attributes.MOVEMENT_SPEED, DASH_ID));
	}

	public static float getEffectiveCooldown(HappyGhast ghast) {
		return applySpeedBoost(ghast, getStatFromModifier(ghast, Attributes.FOLLOW_RANGE, COOLDOWN_ID));
	}

	private static float getStatFromModifier(HappyGhast ghast,
			net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
			Identifier modifierId) {
		AttributeInstance instance = ghast.getAttribute(attribute);
		if (instance != null) {
			AttributeModifier modifier = instance.getModifier(modifierId);
			if (modifier != null) {
				return Constants.STAT_DEFAULT + (float) modifier.amount();
			}
		}
		return Constants.STAT_DEFAULT;
	}

	private static float applySpeedBoost(HappyGhast ghast, float baseStat) {
		MobEffectInstance speed = ghast.getEffect(MobEffects.SPEED);
		if (speed != null) {
			return baseStat + (speed.getAmplifier() + 1) * Constants.SPEED_BOOST_PER_LEVEL;
		}
		return baseStat;
	}
}
