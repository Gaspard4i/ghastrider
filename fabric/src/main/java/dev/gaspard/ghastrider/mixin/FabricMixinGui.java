package dev.gaspard.ghastrider.mixin;

import dev.gaspard.ghastrider.client.DashBarRenderer;
import dev.gaspard.ghastrider.client.DashHandler;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class FabricMixinGui {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private DashBarRenderer ghastrider$dashBarRenderer;

    @Redirect(
            method = "extractHotbarAndDecorations",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastrider$redirectExtractBackground(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            ghastrider$getOrCreateRenderer().extractBackground(graphics, deltaTracker);
        } else {
            ghastrider$dashBarRenderer = null;
            original.extractBackground(graphics, deltaTracker);
        }
    }

    @Redirect(
            method = "extractHotbarAndDecorations",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastrider$redirectExtractRenderState(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            ghastrider$getOrCreateRenderer().extractRenderState(graphics, deltaTracker);
        } else {
            original.extractRenderState(graphics, deltaTracker);
        }
    }

    @Unique
    private DashBarRenderer ghastrider$getOrCreateRenderer() {
        if (ghastrider$dashBarRenderer == null) {
            ghastrider$dashBarRenderer = new DashBarRenderer(this.minecraft);
        }
        return ghastrider$dashBarRenderer;
    }
}
