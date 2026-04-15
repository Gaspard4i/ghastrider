package dev.gaspard.ghastdash.mixin;

import com.mojang.datafixers.util.Pair;
import dev.gaspard.ghastdash.client.DashBarRenderer;
import dev.gaspard.ghastdash.client.DashHandler;
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

/**
 * Mixin that replaces the XP bar with our dash bar when riding a Happy Ghast,
 * exactly like vanilla replaces it with the horse jump bar.
 */
@Mixin(Gui.class)
public class MixinGui {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private DashBarRenderer ghastdash$dashBarRenderer;

    /**
     * Intercept the extractBackground call on the contextual bar renderer.
     * When riding a Happy Ghast, substitute our DashBarRenderer.
     */
    @Redirect(
            method = "extractRenderState",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastdash$redirectExtractBackground(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            if (ghastdash$dashBarRenderer == null) {
                ghastdash$dashBarRenderer = new DashBarRenderer(this.minecraft);
            }
            ghastdash$dashBarRenderer.extractBackground(graphics, deltaTracker);
        } else {
            ghastdash$dashBarRenderer = null;
            original.extractBackground(graphics, deltaTracker);
        }
    }

    /**
     * Intercept the extractRenderState call on the contextual bar renderer.
     * When riding a Happy Ghast, substitute our DashBarRenderer.
     */
    @Redirect(
            method = "extractRenderState",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastdash$redirectExtractRenderState(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            if (ghastdash$dashBarRenderer == null) {
                ghastdash$dashBarRenderer = new DashBarRenderer(this.minecraft);
            }
            ghastdash$dashBarRenderer.extractRenderState(graphics, deltaTracker);
        } else {
            original.extractRenderState(graphics, deltaTracker);
        }
    }
}
