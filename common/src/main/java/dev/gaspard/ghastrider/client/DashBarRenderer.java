package dev.gaspard.ghastrider.client;

import dev.gaspard.ghastrider.Constants;
import dev.gaspard.ghastrider.DashState;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

/**
 * Renders the dash charge bar in place of the XP bar, exactly like the horse jump bar.
 * Uses the same ContextualBarRenderer system as JumpableVehicleBarRenderer.
 */
public class DashBarRenderer implements ContextualBarRenderer {

    private static final Identifier DASH_BAR_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("hud/jump_bar_background");
    private static final Identifier DASH_BAR_COOLDOWN_SPRITE = Identifier.withDefaultNamespace("hud/jump_bar_cooldown");
    private static final Identifier DASH_BAR_PROGRESS_SPRITE = Identifier.withDefaultNamespace("hud/jump_bar_progress");

    private final Minecraft minecraft;

    public DashBarRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        int left = this.left(this.minecraft.getWindow());
        int top = this.top(this.minecraft.getWindow());

        // Always draw the background bar
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DASH_BAR_BACKGROUND_SPRITE, left, top, WIDTH, HEIGHT);

        DashState dashState = DashHandler.getState();

        if (dashState.isOnCooldown()) {
            // Cooldown: bar starts at the charge level reached, then shrinks down
            float maxRatio = dashState.getCooldownMaxRatio();
            float cooldownProgress = dashState.getCooldownProgress();
            int cooldownWidth = (int) (WIDTH * maxRatio * cooldownProgress);
            if (cooldownWidth > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DASH_BAR_PROGRESS_SPRITE, WIDTH, HEIGHT, 0, 0, left, top, cooldownWidth, HEIGHT);
            }
        } else if (dashState.isCharging()) {
            float progress = dashState.getChargeProgress();
            int progressWidth = (int) (WIDTH * progress);
            if (progressWidth > 0) {
                graphics.blitSprite(RenderPipelines.GUI_TEXTURED, DASH_BAR_PROGRESS_SPRITE, WIDTH, HEIGHT, 0, 0, left, top, progressWidth, HEIGHT);
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        // No additional render state needed
    }
}
