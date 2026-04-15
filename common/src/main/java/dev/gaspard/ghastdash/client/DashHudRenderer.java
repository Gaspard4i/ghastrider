package dev.gaspard.ghastdash.client;

import dev.gaspard.ghastdash.Constants;
import dev.gaspard.ghastdash.DashState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

/**
 * Renders the dash charge bar on the HUD when riding a Happy Ghast.
 * The bar appears above the XP bar, similar to the horse jump bar.
 */
public class DashHudRenderer {

    // Bar dimensions (same width as the vanilla XP bar)
    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;

    public static void render(GuiGraphicsExtractor guiGraphics, float partialTick) {
        if (!DashHandler.isRidingHappyGhast()) return;

        Minecraft mc = Minecraft.getInstance();
        DashState dashState = DashHandler.getState();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int barX = (screenWidth - BAR_WIDTH) / 2;
        // Position above the XP bar (XP bar is at screenHeight - 29)
        int barY = screenHeight - 32 - BAR_HEIGHT - 3;

        // Draw background (dark bar)
        guiGraphics.fill(barX - 1, barY - 1, barX + BAR_WIDTH + 1, barY + BAR_HEIGHT + 1, 0x80000000);

        if (dashState.isCharging()) {
            // Charging: fill bar from left to right, cyan-to-white gradient
            float progress = dashState.getChargeProgress();
            int fillWidth = (int) (BAR_WIDTH * progress);

            int r = (int) (80 + 175 * progress);
            int g = (int) (220 + 35 * progress);
            int b = 255;
            int color = 0xFF000000 | (r << 16) | (g << 8) | b;

            guiGraphics.fill(barX, barY, barX + fillWidth, barY + BAR_HEIGHT, color);

            // "Sweet spot" marker at 80-100% (golden zone like horse jump)
            if (progress >= 0.8f) {
                int sweetSpotColor = 0xFFFFD700; // Gold
                guiGraphics.fill(barX + fillWidth - 2, barY - 1, barX + fillWidth, barY + BAR_HEIGHT + 1, sweetSpotColor);
            }
        } else if (dashState.isOnCooldown()) {
            // Cooldown: grey bar draining from right to left
            float cooldownProgress = dashState.getCooldownProgress();
            int fillWidth = (int) (BAR_WIDTH * cooldownProgress);
            guiGraphics.fill(barX, barY, barX + fillWidth, barY + BAR_HEIGHT, 0xFF666666);
        } else {
            // Ready: show a subtle "ready" indicator
            guiGraphics.fill(barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, 0x4050DDFF);
        }
    }
}
