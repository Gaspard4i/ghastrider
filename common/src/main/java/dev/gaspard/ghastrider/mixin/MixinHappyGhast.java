package dev.gaspard.ghastrider.mixin;

import dev.gaspard.ghastrider.IceEffectHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HappyGhast.class)
public class MixinHappyGhast {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void ghastrider$onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        HappyGhast self = (HappyGhast) (Object) this;
        ItemStack stack = player.getItemInHand(hand);
        InteractionResult result = IceEffectHelper.handleIceInteraction(self, player, hand, stack);
        if (result != null) {
            cir.setReturnValue(result);
        }
    }
}
