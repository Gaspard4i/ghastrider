package dev.gaspard.ghastrider;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
            playEatingEffects(ghast, stack, (ServerLevel) ghast.level());
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        if (stack.is(Items.POWDER_SNOW_BUCKET)) {
            applySpeedEffect(ghast, 0);
            playEatingEffects(ghast, stack, (ServerLevel) ghast.level());
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

    private static void playEatingEffects(HappyGhast ghast, ItemStack stack, ServerLevel level) {
        double x = ghast.getX();
        double y = ghast.getY() + ghast.getBbHeight() * 0.6;
        double z = ghast.getZ();

        // Ice crunch eating sound
        level.playSound(null, x, y, z, ModSounds.ICE_FEED, SoundSource.NEUTRAL, 0.8F, 0.9F + level.getRandom().nextFloat() * 0.2F);

        // Happy ghast purr
        level.playSound(null, x, y, z, ModSounds.GHAST_HAPPY, SoundSource.NEUTRAL, 0.6F, 1.0F + level.getRandom().nextFloat() * 0.2F);

        // Item crack particles around the mouth
        ItemParticleOption particle = new ItemParticleOption(ParticleTypes.ITEM, stack.getItem());
        for (int i = 0; i < 8; i++) {
            double ox = (level.getRandom().nextDouble() - 0.5) * 0.8;
            double oy = level.getRandom().nextDouble() * 0.4;
            double oz = (level.getRandom().nextDouble() - 0.5) * 0.8;
            level.sendParticles(particle, x + ox, y + oy, z + oz, 1, 0, 0.05, 0, 0.05);
        }
    }

    private static int getIceAmplifier(ItemStack stack) {
        if (stack.is(Items.ICE)) return 0;          // Speed I
        if (stack.is(Items.PACKED_ICE)) return 1;    // Speed II
        if (stack.is(Items.BLUE_ICE)) return 2;      // Speed III
        return -1;
    }
}
