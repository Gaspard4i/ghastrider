package dev.gaspard.ghastrider.mixin;

import dev.gaspard.ghastrider.Constants;
import dev.gaspard.ghastrider.GhastBreedGoal;
import dev.gaspard.ghastrider.GhastBreedingHelper;
import dev.gaspard.ghastrider.GhastStatsAccess;
import dev.gaspard.ghastrider.IceEffectHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HappyGhast.class)
public abstract class MixinHappyGhast extends Animal implements GhastStatsAccess {

	@Unique
	private static final Identifier GHASTRIDER_SPEED_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "speed_stat");
	@Unique
	private static final Identifier GHASTRIDER_DASH_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "dash_strength_stat");
	@Unique
	private static final Identifier GHASTRIDER_COOLDOWN_ID =
			Identifier.fromNamespaceAndPath(Constants.MOD_ID, "cooldown_factor_stat");

	@Unique
	private float ghastrider$speed = Constants.STAT_DEFAULT;
	@Unique
	private float ghastrider$dashStrength = Constants.STAT_DEFAULT;
	@Unique
	private float ghastrider$cooldownFactor = Constants.STAT_DEFAULT;
	@Unique
	private boolean ghastrider$statsInitialized = false;

	protected MixinHappyGhast() {
		super(null, null);
	}

	// --- GhastStatsAccess implementation ---

	@Override
	public float ghastrider$getSpeed() {
		return ghastrider$speed;
	}

	@Override
	public float ghastrider$getDashStrength() {
		return ghastrider$dashStrength;
	}

	@Override
	public float ghastrider$getCooldownFactor() {
		return ghastrider$cooldownFactor;
	}

	@Override
	public void ghastrider$setStats(float speed, float dashStrength, float cooldown) {
		this.ghastrider$speed = speed;
		this.ghastrider$dashStrength = dashStrength;
		this.ghastrider$cooldownFactor = cooldown;
		this.ghastrider$statsInitialized = true;
		ghastrider$syncModifiers();
	}

	@Override
	public boolean ghastrider$hasStats() {
		return ghastrider$statsInitialized;
	}

	@Unique
	private void ghastrider$syncModifiers() {
		// Use attribute modifiers on FLYING_SPEED and MOVEMENT_SPEED to sync stats to client.
		// The modifier amount encodes our stat value minus default (so 0.0 = default).
		// FLYING_SPEED carries speed stat, MOVEMENT_SPEED carries dash strength.
		// Cooldown factor is encoded on FOLLOW_RANGE (not gameplay-relevant for riding).
		ghastrider$setModifier(Attributes.FLYING_SPEED, GHASTRIDER_SPEED_ID, ghastrider$speed - Constants.STAT_DEFAULT);
		ghastrider$setModifier(Attributes.MOVEMENT_SPEED, GHASTRIDER_DASH_ID, ghastrider$dashStrength - Constants.STAT_DEFAULT);
		ghastrider$setModifier(Attributes.FOLLOW_RANGE, GHASTRIDER_COOLDOWN_ID, ghastrider$cooldownFactor - Constants.STAT_DEFAULT);
	}

	@Unique
	private void ghastrider$setModifier(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
			Identifier id, double amount) {
		AttributeInstance instance = this.getAttribute(attribute);
		if (instance != null) {
			instance.removeModifier(id);
			instance.addPermanentModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE));
		}
	}

	// --- NBT persistence ---

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void ghastrider$saveData(ValueOutput output, CallbackInfo ci) {
		ValueOutput stats = output.child("GhastRider");
		stats.putFloat("Speed", ghastrider$speed);
		stats.putFloat("DashStrength", ghastrider$dashStrength);
		stats.putFloat("CooldownFactor", ghastrider$cooldownFactor);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void ghastrider$loadData(ValueInput input, CallbackInfo ci) {
		input.child("GhastRider").ifPresent(stats -> {
			float speed = stats.getFloatOr("Speed", Constants.STAT_DEFAULT);
			float dash = stats.getFloatOr("DashStrength", Constants.STAT_DEFAULT);
			float cooldown = stats.getFloatOr("CooldownFactor", Constants.STAT_DEFAULT);
			ghastrider$setStats(speed, dash, cooldown);
		});
		if (!ghastrider$statsInitialized) {
			GhastBreedingHelper.randomizeStats((HappyGhast) (Object) this, this.random);
		}
	}

	// --- Add BreedGoal to AI (vanilla HappyGhast has none) ---

	@Inject(method = "registerGoals", at = @At("TAIL"))
	private void ghastrider$addBreedGoal(CallbackInfo ci) {
		this.goalSelector.addGoal(2, new GhastBreedGoal((HappyGhast) (Object) this));
	}

	// --- Breeding: snowball as food ---

	@Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
	private void ghastrider$isFood(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.is(Items.SNOWBALL)) {
			cir.setReturnValue(true);
		}
	}

	// --- Enable love mode (vanilla returns false) ---

	@Inject(method = "canFallInLove", at = @At("HEAD"), cancellable = true)
	private void ghastrider$canFallInLove(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!this.isBaby() && this.getAge() == 0 && !this.isInLove());
	}

	// --- Intercept mobInteract to handle snowball breeding before ride logic ---

	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	private void ghastrider$onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		HappyGhast self = (HappyGhast) (Object) this;
		ItemStack stack = player.getItemInHand(hand);

		// Ice feeding
		InteractionResult iceResult = IceEffectHelper.handleIceInteraction(self, player, hand, stack);
		if (iceResult != null) {
			cir.setReturnValue(iceResult);
			return;
		}

		// Snowball breeding: intercept before HappyGhast rides the player
		if (stack.is(Items.SNOWBALL) && !this.isBaby() && this.getAge() == 0 && this.canFallInLove()) {
			if (player instanceof ServerPlayer) {
				this.usePlayerItem(player, hand, stack);
				this.setInLove(player);
				this.playEatingSound();
			}
			cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
		}
	}

	// --- Breeding: stat inheritance ---

	@Inject(method = "getBreedOffspring", at = @At("RETURN"), cancellable = true)
	private void ghastrider$onBreedOffspring(ServerLevel level, AgeableMob partner,
			CallbackInfoReturnable<AgeableMob> cir) {
		AgeableMob baby = cir.getReturnValue();
		if (baby instanceof HappyGhast babyGhast && partner instanceof HappyGhast partnerGhast) {
			GhastBreedingHelper.inheritStats(
					babyGhast,
					(HappyGhast) (Object) this,
					partnerGhast,
					level.getRandom()
			);
		}
	}
}
