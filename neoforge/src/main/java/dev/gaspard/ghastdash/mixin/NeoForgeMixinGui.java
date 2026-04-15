package dev.gaspard.ghastdash.mixin;

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

@Mixin(Gui.class)
public class NeoForgeMixinGui {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Unique
    private DashBarRenderer ghastdash$dashBarRenderer;

    @Redirect(
            method = "extractContextualInfoBarBackground",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastdash$redirectExtractBackground(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            ghastdash$getOrCreateRenderer().extractBackground(graphics, deltaTracker);
        } else {
            ghastdash$dashBarRenderer = null;
            original.extractBackground(graphics, deltaTracker);
        }
    }

    @Redirect(
            method = "extractContextualInfoBar",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/contextualbar/ContextualBarRenderer;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V")
    )
    private void ghastdash$redirectExtractRenderState(ContextualBarRenderer original, GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (DashHandler.isRidingHappyGhast()) {
            ghastdash$getOrCreateRenderer().extractRenderState(graphics, deltaTracker);
        } else {
            original.extractRenderState(graphics, deltaTracker);
        }
    }

    @Unique
    private DashBarRenderer ghastdash$getOrCreateRenderer() {
        if (ghastdash$dashBarRenderer == null) {
            ghastdash$dashBarRenderer = new DashBarRenderer(this.minecraft);
        }
        return ghastdash$dashBarRenderer;
    }
}
