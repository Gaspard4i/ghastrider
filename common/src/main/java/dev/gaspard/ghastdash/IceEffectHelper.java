package dev.gaspard.ghastdash;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public class IceEffectHelper {

    private static final int EFFECT_DURATION_TICKS = 200; // 10 seconds

    @Nullable
    public static InteractionResult handleIceInteraction(HappyGhast ghast, Player player, InteractionHand hand, ItemStack stack) {
        if (!(ghast.level() instanceof ServerLevel)) return null;

        int amplifier = getIceAmplifier(stack);
        if (amplifier >= 0) {
            applySpeedEffect(ghast, amplifier);
            ghast.level().playSound(null, ghast.getX(), ghast.getY(), ghast.getZ(),
                    SoundEvents.POWDER_SNOW_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.POWDER_SNOW_BUCKET)) {
            applySpeedEffect(ghast, 0);
            ghast.level().playSound(null, ghast.getX(), ghast.getY(), ghast.getZ(),
                    SoundEvents.BUCKET_EMPTY_POWDER_SNOW, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
            return InteractionResult.SUCCESS;
        }

        return null;
    }

    private static void applySpeedEffect(HappyGhast ghast, int amplifier) {
        int duration = EFFECT_DURATION_TICKS;
        MobEffectInstance existing = ghast.getEffect(MobEffects.SPEED);
        if (existing != null && existing.getAmplifier() == amplifier) {
            // Same ice type: stack duration
            duration = existing.getDuration() + EFFECT_DURATION_TICKS;
        }
        // Different ice type or no existing effect: reset to 10 sec with new amplifier
        ghast.addEffect(new MobEffectInstance(MobEffects.SPEED, duration, amplifier, false, true));
    }

    private static int getIceAmplifier(ItemStack stack) {
        if (stack.is(Items.ICE)) return 0;          // Speed I
        if (stack.is(Items.PACKED_ICE)) return 1;    // Speed II
        if (stack.is(Items.BLUE_ICE)) return 2;      // Speed III
        return -1;
    }
}
